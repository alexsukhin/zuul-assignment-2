 /**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Inventory inventory;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms(); 
        parser = new Parser();
        inventory = new Inventory(6);
    }

    /**
     * Create all the rooms and link their exits and items together.
     */
    private void createRooms()
    {
        Room wreck, cabin, forest, lake, camp, cave, station, storm, rescue;
      
        // create the rooms
        wreck = new Room("amid the remains of your crashed plane, frigid winds sweeping through");
        cabin = new Room("inside an old wooden cabin, cold but sheltered from the wind");
        forest = new Room("surrounded by dense, snow-laden trees, with quiet animal tracks nearby");
        lake = new Room("at the edge of a vast frozen lake, cracks spiderwebbing beneath your feet");
        camp = new Room("in a destroyed camp, ravaged by wolves, with torn tents and scattered belongings");
        cave = new Room("in a glistening ice cave, the air chilling and echoes bouncing around");
        station = new Room("inside an abandoned research station, tools scattered across tables");
        storm = new Room("in the midst of a blinding snowstorm, with ice and snow whipping around you");
        rescue = new Room("at a flat, open expanse, perfect for signaling with the remnants of an old fire nearby");
        
        // initialise room exits
        wreck.setExit("east", cabin);
        wreck.setExit("north", lake);
        
        cabin.setExit("west", wreck);
        cabin.setExit("east", forest);
        cabin.setExit("north", lake);
        
        forest.setExit("west", cabin);
        
        lake.setExit("south", wreck);
        lake.setExit("east", cabin);
        lake.setExit("north", camp);
        
        camp.setExit("north", cave);
        camp.setExit("south", lake);
        
        cave.setExit("south", camp);
        cave.setExit("north", storm);
        cave.setExit("east", station);
        
        
        station.setExit("west", cave);
        station.setExit("north", storm);
        
        storm.setExit("south", cave);
        storm.setExit("east", station);
        storm.setExit("north", rescue);
        
        // create the items
        Item rope = new Item("Rope", "A sturdy rope, useful for climbing or crossing difficult terrain.", 2);
        Item brokenRadio = new Item("Radio", "An old radio transmitter. It looks like it could be repaired.", 3);
        Item tools = new Item("Tools", "A set of tools, possibly useful for fixing the broken radio.", 4);
        Item firewood = new Item("Firewood", "Some dry firewood. It could be used to start a fire.", 2);
        Item battery = new Item("Battery", "A spare battery, could be useful for powering devices.", 1);
        Item armour = new Item("Armour", "Thick leather armour, insulated to keep you warm.", 5);
        Item torch = new Item("Torch", "A burning torch that can scare off wild animals.", 2);
        
        //initialise the items
        wreck.addItem(brokenRadio);
        
        cabin.addItem(rope); //after lighting on fire unlocks level
        
        forest.addItem(firewood);
        forest.addItem(armour);
        
        camp.addItem(torch);
        camp.addItem(tools);
        
        station.addItem(battery);
        
        currentRoom = wreck;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println(currentRoom.getItemString());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("inventory")) {
            inventory.showInventory();  
        }
        else if (commandWord.equals("get")) {
            pickupItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("search")) {
            search(command);
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
    private void printHelp() 
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
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            System.out.println(currentRoom.getItemString());
        }
    }
    
    private void pickupItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Pick up which item?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        Item item = currentRoom.getItem(itemName);
        
        if (item == null) {
            System.out.println("This is not an item!");
        } else {
            currentRoom.removeItem(itemName);
            inventory.addItem(item);
        }
    }
    
    private void dropItem(Command command)
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
    
    private void search(Command command)
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
