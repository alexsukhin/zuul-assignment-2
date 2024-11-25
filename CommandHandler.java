import java.util.HashMap;

/**
 * Write a description of class CommandHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CommandHandler
{
    
        /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processCommand(Command command, GameState gameState, Parser parser) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        
        Room currentRoom = gameState.getCurrentRoom();
        Inventory inventory = gameState.getInventory();

        String commandWord = command.getCommandWord();
        switch (commandWord) {
            case "help":
                printHelp(parser);
                break;
            case "go":
                goRoom(command, gameState);
                break;
            case "quit":
                wantToQuit = quit(command);
                break;
            case "inventory":
                inventory.showInventory();
                break;
            case "get":
                pickupItem(command, gameState);
                break;
            case "drop":
                dropItem(command, currentRoom, inventory);
                break;
            case "search":
                search(command, currentRoom);
                break;
            case "examine":
                examine(command, gameState);
                break;
            case "rest":
                rest(command, gameState);
                break;
            case "use":
                use(command, gameState);
                break;
            case "craft":
                craft(command, gameState);
                break;
            case "give":
                give(command, gameState);
                break;
            case "back":
                back(command, gameState);
                break;
            default:
                System.out.println("I don't understand this command.");
        }
        
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp(Parser parser) 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command, GameState gameState) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        
        String direction = command.getSecondWord();
        Room previousRoom = gameState.getPreviousRoom();
        Room currentRoom = gameState.getCurrentRoom();
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
            return;
        }
        
        if (nextRoom.isTeleporter()) {
            gameState.teleportRandomRoom(nextRoom);
            return;
        }
        
        Entity entity = currentRoom.getEntity("lake");
        
        if (entity instanceof Unlockable) {
            if (!gameState.canTraverseLake(currentRoom, previousRoom, nextRoom, entity)) {
                System.out.println("You must first traverse the lake using a rope!");
                return;
            }
        }
        
        gameState.adjustHeat(-(nextRoom.getHeat()));
        gameState.addRoomToStack(currentRoom);
        
        gameState.setCurrentRoom(nextRoom);
        
        if (gameState.handleEntityEncounters(currentRoom)) {
            System.out.println(currentRoom.getLongDescription());
        } else {
            System.out.println(nextRoom.getLongDescription());
        }
        
        System.out.println(gameState.displayHeat());
                    
    }

    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    private void pickupItem(Command command, GameState gameState)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("There is no such item here!");
            return;
        }
        
        String itemName = command.getSecondWord();
        Room currentRoom = gameState.getCurrentRoom();
        Inventory inventory = gameState.getInventory();
        
        Item item = currentRoom.getItem(itemName);
        
        if (item == null) {
            System.out.println("This is not an item!");
        } else {
            if (item.canPickUp()) {
                if (gameState.belowWeightLimit(item)) {
                    currentRoom.removeItem(itemName);
                    inventory.addItem(item);
                    System.out.println("You picked up " + item.getName());
                } else {
                    System.out.println("You can't carry any more items. Max weight reached.");
                }                
            } else {
                System.out.println("You cannot pick this up.");
            }
        }
    }
    
    private void dropItem(Command command, Room currentRoom, Inventory inventory)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Drop which item?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        Item item = inventory.getItem(itemName);

        
        if (item == null) {
            System.out.println("This is not an item!");
        } else {
            currentRoom.addItem(item);
            inventory.removeItem(item);
        }
    }
    
    private void search(Command command, Room currentRoom)
    {
        if (command.hasSecondWord()) {
            System.out.println("Search what?");
        } else {
            System.out.println(currentRoom.getItemString());
        }
    }
    
    private void examine(Command command, GameState gameState)
    {        
        if (!command.hasSecondWord()) {
            System.out.println("Examine what?");
            return;
        }
        
        Room currentRoom = gameState.getCurrentRoom();
        String objectName = command.getSecondWord();
        
        if (currentRoom.hasItem(objectName)) {
            Item item = currentRoom.getItem(objectName);
            System.out.println("You examine the " + item.getName() + ": " + item.getDescription());
            return;
        }
        
        if (currentRoom.hasEntity(objectName)) {
            Entity entity = currentRoom.getEntity(objectName);
            entity.examine(gameState);
            return;
        }
        
        System.out.println("There is no such thing to examine here.");
        
    }
    
    private void rest(Command command, GameState gameState)
    {
        Room currentRoom = gameState.getCurrentRoom();
        
        if (!command.hasSecondWord()) {
            System.out.println("Rest on what?");
            return;
        }
        
        String entityName = command.getSecondWord();
        
        if (currentRoom.hasEntity(entityName)) {
            Entity entity = currentRoom.getEntity(entityName);
            Unlockable unlockable = (Unlockable) entity;
            unlockable.rest(gameState);
            System.out.println(gameState.displayHeat());
            return;
        }
        
        System.out.println("There is no such thing to interact with here.");
        
    }
    
    public void use(Command command, GameState gameState)
    {
        if (!command.hasSecondWord() && !command.hasThirdWord()) {
            System.out.println("Use what?");
            return;
        } else if (!command.hasThirdWord()) {
            System.out.println("Use the item on what?");
            return;
        }
        
        Room currentRoom = gameState.getCurrentRoom();
        Inventory inventory = gameState.getInventory();
        
        String itemName = command.getSecondWord();
        String entityName = command.getThirdWord();
        
        Item item = inventory.getItem(itemName);
        Entity entity = currentRoom.getEntity(entityName);
    
        if (item == null) {
            System.out.println("Your item is not a valid item!");
            return;
        } else if (entity == null) {
            System.out.println("Your entity is not a valid entity!");
            return;
        }
        
        if (entity instanceof Unlockable) {
            Unlockable unlockable = (Unlockable) entity;
            unlockable.interact(gameState, item);
        } else if (entity instanceof Deer) {
            gameState.handleEntityKill(entity, currentRoom, item);
        }
    }
    
    public void craft(Command command, GameState gameState)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Craft what?");
            return;
        }
        
        Room currentRoom = gameState.getCurrentRoom();
        
        String itemName = command.getSecondWord();
    
        if (currentRoom.hasEntity("crafting-table")) {
            Entity entity = currentRoom.getEntity("crafting-table");
            CraftingTable craftingTable = (CraftingTable) entity;
            
            if (craftingTable.craftItem(gameState, itemName)) {
                if (itemName.equals("armour")) {
                    System.out.println("You have equipped the leather armour! [+50 Max Heat]");
                    gameState.setMaxHeat(150);
                    System.out.println(gameState.displayHeat());
                }
            } else {
                System.out.println("You have no tools in your inventory!");
            }
            
            
        } else {
            System.out.println("There is no crafting table in this room!");
            return;
        }
    }
    
    public void give(Command command, GameState gameState)
    {
        if (!command.hasSecondWord() && !command.hasThirdWord()) {
            System.out.println("Give what?");
            return;
        } else if (!command.hasThirdWord()) {
            System.out.println("Give the item to who?");
            return;
        }
        
        Room currentRoom = gameState.getCurrentRoom();
        Inventory inventory = gameState.getInventory();
        
        String itemName = command.getSecondWord();
        String entityName = command.getThirdWord();
        
        Item item = inventory.getItem(itemName);
        Entity entity = currentRoom.getEntity(entityName);
    
        if (item == null) {
            System.out.println("Your item is not a valid item!");
            return;
        } else if (entity == null) {
            System.out.println("Your entity is not a valid entity!");
            return;
        }
        
        if (entity instanceof Person) {
            Person person = (Person) entity;
            person.interact(gameState, item);
        }
    }
    
    public void back(Command command, GameState gameState)
    {
        if (!gameState.Back()) {
            System.out.println("There is no previous room to go back to!");
        } else {
            System.out.println("Returning to previous room!");
            System.out.println(gameState.getCurrentRoom().getLongDescription());   
        }
    }

    
}
