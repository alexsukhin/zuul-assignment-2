import java.util.HashMap;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a simple, text-based adventure game. Players can walk around the scenery, 
 * solve puzzles, and complete objectives.
 * 
 * manages the game logic, including initializing the game, handling commands, 
 * tracking the player's state, and determining win/lose conditions. The `play` method loops 
 * until the game ends.
 * 
 * To play this game, instantiate this class and call the `play` method.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2024.11.26
 */
public class Game 
{
    private Parser parser;           // the command parser
    private GameState gameState;     // the game state, tracking rooms, items, and entities
    private CommandHandler commandHandler; // the handler for processing commands
    
    /**
     * Creates the game and initialises its internal map.
     */
    public Game() 
    {
        initialiseGame();
    }

    /**
     * Initialises the game state, creates rooms, items, and entities, and sets up the parser 
     * and command handler.
     */
    private void initialiseGame()
    {
        WorldBuilder builder = new WorldBuilder();
        
        // Creates rooms, items, and entities
        HashMap<String, Room> rooms = builder.createRooms();
        HashMap<String, Item> items = builder.createItems(rooms);
        HashMap<String, Entity> entities = builder.createEntities(rooms, items);
        
        // Creates game state
        gameState = new GameState(rooms, items, entities);
        
        // Initialises parser and command handler
        parser = new Parser();
        commandHandler = new CommandHandler();
    }

    // Main game loop

    /**
     * The main game loop that continues until the game ends.
     * This method processes user commands and checks for win/lose conditions.
     */
    public void play() 
    {
        boolean finished = false;

        while (!finished) {
            printWelcome();
            
            // Processes commands in an inner loop
            while (true) {
                Command command = parser.getCommand();
                boolean exitGame = commandHandler.processCommand(command, gameState, parser);
                
                // Checks for death due to cold
                if (gameState.getCurrentHeat() <= 0) {
                    System.out.println("You have frozen to death!");
                    System.out.println("Restarting the game...");
                    delay(1000);
                    initialiseGame();
                    break;
                }
                
                // Exits the game if requested
                if (exitGame) {
                    finished = true;
                    break;
                }

                // Checks for winning or losing conditions
                if (gameState.checkFinalRoom()) {
                    if (gameState.checkFinalItem()) {
                        System.out.println("You transmit the signal using the repaired radio.");
                        System.out.println("Moments later, the faint hum of a helicopter grows louder, and a spotlight cuts through the storm.");
                        System.out.println("Relief washes over you as you're pulled aboard, leaving the frozen wilderness behind.");
                        finished = true;
                        break;
                    } else {
                        System.out.println("You reach the rescue pad, but without a working radio, your signal cannot be sent.");
                        System.out.println("The storm rages on, and rescue feels impossibly far away.");
                        System.out.println("Restarting the game...");
                        delay(1000);
                        initialiseGame();
                        break;
                    }
                }
            }
        }

        System.out.println("\nThank you for playing!");
    }

    // Helper methods

    /**
     * Prints the welcome message to the player, including game description and current room details.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(gameState.getCurrentRoom().getLongDescription());
        System.out.println(gameState.getCurrentRoom().getEntitiesString());
        System.out.println(gameState.displayHeat());
    }

    /**
     * Introduces a delay in the game for the given number of milliseconds.
     * 
     * @param milliseconds The number of milliseconds to delay the game for.
     */
    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Something interrupted the delay!");
        }
    }
}
