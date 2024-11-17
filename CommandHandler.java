
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
                gameState.getInventory().showInventory();
                break;
            case "get":
                pickupItem(command, gameState);
                break;
            case "drop":
                dropItem(command, gameState);
                break;
            case "search":
                search(command, gameState.getCurrentRoom());
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
        Room currentRoom = gameState.getCurrentRoom();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            gameState.setCurrentRoom(nextRoom);
            System.out.println(currentRoom.getLongDescription());
            System.out.println(currentRoom.getItemString());
        }
    }
    
    private void pickupItem(Command command, GameState gameState)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Pick up which item?");
            return;
        }
        
        String itemName = command.getSecondWord();
        Room currentRoom = gameState.getCurrentRoom();
        Inventory inventory = gameState.getInventory();
        
        Item item = currentRoom.getItem(itemName);
        
        if (item == null) {
            System.out.println("This is not an item!");
        } else {
            currentRoom.removeItem(itemName);
            inventory.addItem(item);
        }
    }
    
    private void dropItem(Command command, GameState gameState)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Drop which item?");
            return;
        }
        
        String itemName = command.getSecondWord();
        Room currentRoom = gameState.getCurrentRoom();
        Inventory inventory = gameState.getInventory();
        
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
    
}
