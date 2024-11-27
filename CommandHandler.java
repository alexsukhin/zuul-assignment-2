import java.util.HashMap;

/**
 * This class is responsible for processing and executing player commands in the game.
 * It takes in a command, checks what actions need to be taken, and interacts with
 * the game state accordingly.
 * 
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class CommandHandler
{
    /**
     * Retrieves the command received from the user, identifies the command
     * and calls the appropriate method representing the command.
     * 
     * 
     * @param command The command to be processed.
     * @param gameState The current state of the game, including the player's inventory and location.
     * @param parser The parser used to retrieve the commands.
     * @return true if the command should end the game, false otherwise.
     */
    public boolean processCommand(Command command, GameState gameState, Parser parser) 
    {
        boolean wantToQuit = false;

        // If the command is not recognized, returns false
        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        
        // Gets the current room and the player's inventory from the game state
        Room currentRoom = gameState.getCurrentRoom();
        Inventory inventory = gameState.getInventory();

        // Switch statement to handle commands
        String commandWord = command.getCommandWord();
        switch (commandWord) {
            case "help":
                printHelp(parser); // Shows the list of commands
                break;
            case "go":
                goRoom(command, gameState); // Handles room movement and room events
                break;
            case "quit":
                wantToQuit = quit(command); // Handles quitting the game
                break;
            case "inventory":
                inventory.showInventory(); // Shows the player's inventory
                System.out.println("Weight: " + inventory.getTotalWeight() + "/" + inventory.getMaxWeight()); // Gets max weight limit
                break;
            case "get":
                pickupItem(command, gameState); // Pick up an item
                break;
            case "drop":
                dropItem(command, currentRoom, inventory); // Drop an item
                break;
            case "search":
                search(command, currentRoom); // Searches the room for items
                break;
            case "examine":
                examine(command, gameState); // Examines an object in the room
                break;
            case "rest":
                rest(command, gameState); // Rest at a rest point
                break;
            case "use":
                use(command, gameState); // Use an item on an entity
                break;
            case "craft":
                craft(command, gameState); // Craft an item using ingredients
                break;
            case "give":
                give(command, gameState); // Give an item to a trader for another item
                break;
            case "back":
                back(command, gameState); // Goes back to the previous room
                break;
            default:
                System.out.println("I don't understand this command.");
        }
        return wantToQuit; // returns whether the game should quit based on the command
    }

    // Helper methods

    /**
     * Displays information and the list of available commands.
     * 
     * @param parser The parser used to retrieve the list of commands.
     */
    private void printHelp(Parser parser) 
    {
        System.out.println("You are lost. You are alone. You wander around...");
        System.out.println("Your command words are:");
        parser.showCommands(); // shows the list of commands
    }
    
    /**
     * Handles the go command to move between rooms. It moves the player
     * to the next room if a valid direction is provided. It also handles
     * game events such as entering a teleporter room and entity events.
     * 
     * @param command The command containing the direction.
     * @param gameState The current state of the game.
     */
    private void goRoom(Command command, GameState gameState) 
    {
        if (!command.hasSecondWord()) {
            // Notify the player if no direction was entered
            System.out.println("Go where?");
            return;
        }
        
        String direction = command.getSecondWord();
        Room currentRoom = gameState.getCurrentRoom();
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            // Notify the player if an invalid direction was entered
            System.out.println("There is no door!");
            return;
        }
        
        // Handles teleportation if the room is a teleporter
        if (nextRoom.isTeleporter()) {
            gameState.teleportRandomRoom(nextRoom); // teleports to a random room
            return;
        }
        
        // Checks if there is a lake entity that prevents the player from moving
        Entity entity = currentRoom.getEntity("lake");
        String previousRoomName = gameState.getPreviousRoom();
        Room previousRoom = gameState.getRoom(previousRoomName);
        
        if (entity instanceof Unlockable) {
            if (!gameState.canTraverseLake(currentRoom, previousRoom, nextRoom, entity)) {
                System.out.println("You must first traverse the lake using a rope!");
                return;
            }
        }
        
        // Adjusts the player's heat based on the new room's heat value
        gameState.adjustHeat(-(nextRoom.getHeat()));
        gameState.addRoomToStack(currentRoom); // Saves the current room to the stack to be able to go back to previous rooms
        gameState.setCurrentRoom(nextRoom); // Sets the current room as the next room
        // Prints the description of the new room based on the outcome of handling entity encounters
        System.out.println(gameState.handleEntityEncounters(currentRoom) ? currentRoom.getLongDescription() : nextRoom.getLongDescription());
        // Displays the updated heat status after moving
        System.out.println(gameState.displayHeat());
    }

    /**
     * Handles the quit command to exit the game if the player
     * wants to.
     * 
     * @param command The command containing the word 'quit'.
     * @return true if the game should end, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if (command.hasSecondWord()) {
            // Ask the player for clarification if there is an extra word
            System.out.println("Quit what?");
            return false;
        }
        return true; // the player wants to quit the game
    }

    /**
     * Handles the get command to pick items from a room.
     * 
     * @param command The command containing the item to pick up.
     * @param gameState The current state of the game.
     */
    private void pickupItem(Command command, GameState gameState)
    {
        if (!command.hasSecondWord()) {
            // Notify the player if no item is specified
            System.out.println("There is no such item here!"); 
            return;
        }
        
        String itemName = command.getSecondWord();
        Room currentRoom = gameState.getCurrentRoom();
        Inventory inventory = gameState.getInventory();
        Item item = currentRoom.getItem(itemName);
        
        if (item == null) {
            
            // Notify the player if the item is not found in the player room
            System.out.println("This is not an item!");
        } else {
            // If the item can be picked up and the player has enough weight capacity
            if (item.canPickUp()) {
                if (gameState.belowWeightLimit(item)) {
                    currentRoom.removeItem(itemName); // removes the item from the room
                    inventory.addItem(itemName); // adds the item to the player's inventory
                    System.out.println("You picked up " + item.getName());
                } else {
                    // Notify the player if they have reached max weight
                    System.out.println("You can't carry any more items. Max weight reached.");
                }                
            } else {
                // Notify the player if the item is not pickupable
                System.out.println("You cannot pick this up.");
            }
        }
    }
    
    /**
     * Handles the drop command to drop items from the player's inventory
     * 
     * @param command The command containing the item to drop.
     * @param currentRoom The current room where the item will be dropped.
     * @param inventory The player's inventory.
     */
    private void dropItem(Command command, Room currentRoom, Inventory inventory)
    {
        if (!command.hasSecondWord()) {
            // Notify the player if no item is specified
            System.out.println("Drop which item?");
            return;
        }
        
        String itemName = command.getSecondWord();
        Item item = inventory.getItem(itemName);
        
        if (item == null) {
            // Notify the player if the item is not found in the inventory
            System.out.println("This is not an item!");
        } else {
            // Remove the item from the inventory and add it to the room
            currentRoom.addItem(item);
            inventory.removeItem(itemName);
            System.out.println("You dropped " + item.getName());
        }
    }
    
    /**
     * Handles the search command to search the current room for items
     * 
     * @param command The commanding containing the search action.
     * @param currentRoom The current room to search.
     */
    private void search(Command command, Room currentRoom)
    {
        if (command.hasSecondWord()) {
            // Notify the player if a second word is specified
            System.out.println("Search what?");
        } else {
            // If no second word is given, list the items in the room
            System.out.println(currentRoom.getItemString());
        }
    }
    
    /**
     * Handles the examine command to examine an item or entity in the room.
     * 
     * @param command The command containing the object to examine.
     * @param gameState The current game state.
     */
    private void examine(Command command, GameState gameState)
    {        
        if (!command.hasSecondWord()) {
            // Notify the player if no object is specified to examine
            System.out.println("Examine what?");
            return;
        }
        
        Room currentRoom = gameState.getCurrentRoom();
        String objectName = command.getSecondWord();
        
        // Check if the object is an item in the room
        if (currentRoom.hasItem(objectName)) {
            Item item = currentRoom.getItem(objectName);
            System.out.println("You examine the " + item.getName() + ": " + item.getDescription());
            return;
        }
        
        // Check if the object is an entity in the room
        if (currentRoom.hasEntity(objectName)) {
            Entity entity = currentRoom.getEntity(objectName);
            entity.examine(); // Examines the entity
            return;
        }
        
    }
    
    /**
     * Handles the rest command where the player can rest on a RestingPlace entity
     * to gain heat.
     * 
     * @param command The command containing the entity to rest on.
     * @param gameState The current game state.
     */
    private void rest(Command command, GameState gameState) 
    {
        if (!command.hasSecondWord()) {
            // Notifies the player if a entity to rest on was not specified
            System.out.println("Rest on what?");
            return;
        }

        String entityName = command.getSecondWord();
        Room currentRoom = gameState.getCurrentRoom();

        // Checks if the current room contains the specified entity
        if (currentRoom.hasEntity(entityName)) {
            Entity entity = currentRoom.getEntity(entityName); 

            // Checks if the entity is an instance of a RestingPlace class
            if (entity instanceof RestingPlace) {
                RestingPlace restingPlace = (RestingPlace) entity;
                restingPlace.rest(gameState);
                return;
            } else {
                // Notifies the player if the entity is not a valid resting place
                System.out.println("You cannot rest here.");
            }
        }

        // Notifies the player if the entity is not found in the room
        System.out.println("There is no such thing to rest on here.");
    }

    /**
     * Handles the use command where the player uses an item on entities.
     * 
     * @param command The command containing the item and entity to use an item on.
     * @param gameState The current game state.
     */
    public void use(Command command, GameState gameState)
    {
        if (!command.hasSecondWord() && !command.hasThirdWord()) {
            // Notifies the player if they have not specified an item
            System.out.println("Use what?");
            return;
        } else if (!command.hasThirdWord()) {
            // Notifies the player if they have not specified an entity
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
            // Notifies the player if the item is not valid
            System.out.println("Your item is not a valid item!");
            return;
        } 
        else if (entity == null) {
            // Notifies the player if the entity is not valid
            System.out.println("Your entity is not a valid entity!");
            return;
        }

        // Checks if the entity is an instance of Unlockable
        if (entity instanceof Unlockable) {
            Unlockable unlockable = (Unlockable) entity;
            unlockable.tryUnlock(inventory, itemName); // attempts to unlock the entity using the item
        } 
        // Checks if the entity is an instance of Deer
        else if (entity instanceof Deer) {
            gameState.handleEntityKill(entity, currentRoom, itemName); // handles the interaction with the deer
        }
    }

    /**
     * Handles the craftcommand where the player can craft an item.
     * 
     * @param command The command containing an item to craft.
     * @param gameState The current game state.
     */
    public void craft(Command command, GameState gameState)
    {
        if (!command.hasSecondWord()) {
            // Notifies the player if the item to craft is not specified
            System.out.println("Craft what?");
            return;
        }

        Room currentRoom = gameState.getCurrentRoom();

        String itemName = command.getSecondWord();

        // Checks if the current room contains a crafting table
        if (currentRoom.hasEntity("crafting-table")) {
            Entity entity = currentRoom.getEntity("crafting-table");
            CraftingTable craftingTable = (CraftingTable) entity;

            // Tries to craft the item with the available ingredients in the inventory
            if (craftingTable.craftItem(gameState.getInventory(), itemName)) {
                // If the crafted item is armour, equip it and update max heat
                if (itemName.equals("armour")) {
                    System.out.println("You have equipped the leather armour! [+50 Max Heat]");
                    gameState.setMaxHeat(150); // increases max heat capacity
                    System.out.println(gameState.displayHeat());
                }
            }
            
        } else {
            // Notifies the player if there is no crafting table in this room
            System.out.println("There is no crafting table in this room!");
            return;
        }
    }
    
    /**
     * Handles the give command where the player gives an item to a trader
     * 
     * @param command The command containing the item to give to the trader.
     * @param gameState The current game state.
     */
    public void give(Command command, GameState gameState)
    {
        if (!command.hasSecondWord() && !command.hasThirdWord()) {
            // Notifies the player if they have not specified an item
            System.out.println("Give what?");
            return;
        } else if (!command.hasThirdWord()) {
            // Notifies the player if they have not specified an entity to give the item to
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
            // Notifies the player if the item is not valid
            System.out.println("Your item is not a valid item!");
            return;
        } else if (entity == null) {
            // Notifies the player if the entity is not valid
            System.out.println("Your entity is not a valid entity!");
            return;
        }

        // Checks if the entity is an instance of Trader
        if (entity instanceof Trader) {
            Trader trader = (Trader) entity;
            trader.interact(inventory, itemName); // the player interacts with the trader, giving the item to them
        }
    }

    /**
     * Handles the back command where the player can return to the previous room.
     *
     * @param command The command containing the back action.
     * @param gameState The current game state.
     */
    public void back(Command command, GameState gameState)
    {
        if (!gameState.back()) {
            // Notifies the player if there is no previous room to go back to
            System.out.println("There is no previous room to go back to!");
        } else {
            // Otherwise, go back to the previous room and display its description
            System.out.println("Returning to previous room!");
            System.out.println(gameState.getCurrentRoom().getLongDescription());   
        }
    }

}
