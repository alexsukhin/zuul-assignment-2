import java.util.HashMap;

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
    private GameState gameState;
    private CommandHandler commandHandler;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        initializeGame();
    }
    
    private void initializeGame()
    {
        WorldBuilder builder = new WorldBuilder();
        HashMap<String, Room> rooms = builder.createRooms();
        HashMap<String, Item> items = builder.createItems(rooms);
        builder.placeEntities(rooms, items);
        
        gameState = new GameState(rooms.get("wreck"), rooms.get("rescue"), items);        
        parser = new Parser();
        commandHandler = new CommandHandler();
        
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {
        boolean finished = false;
        
        while (!finished) {
            printWelcome();
            
            while (true) {
                Command command = parser.getCommand();
                boolean exitGame = commandHandler.processCommand(command, gameState, parser);
                
                if (gameState.getCurrentHeat() <= 0) {
                    System.out.println("You have frozen to death!");
                    System.out.println("Restarting the game...");
                    delay(1000);
                    initializeGame();
                    break;
                }
                
                if (exitGame) {
                    finished = true;
                    break;
                }
                
                if (gameState.checkFinalRoom()) {
                    
                    if (gameState.checkFinalItem()) {
                        System.out.println("You transmit the signal using the repaired radio.");
                        System.out.println("Moments later, the faint hum of a helicopter grows louder, and a spotlight cuts through the storm.");
                        System.out.println("Relief washes over you as you're pulled aboard, leaving the frozen wilderness behind.");
                    } else {
                        System.out.println("You reach the rescue pad, but without a working radio, your signal cannot be sent.");
                        System.out.println("The storm rages on, and rescue feels impossibly far away.");
                    }
                    
                    finished = true;
                    break;
                } 
            }
        }
        
        System.out.println("\nThank you for playing!");
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
        System.out.println(gameState.getCurrentRoom().getLongDescription());
        System.out.println(gameState.getCurrentRoom().getEntitiesString());
        System.out.println(gameState.displayHeat());
    }
    
    
    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Something interrupted the delay!");
        }
    }

}
