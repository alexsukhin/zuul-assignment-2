import java.util.List;

/**
 * This class represents a Snow Wolf, a hostile moving entity in the game world.
 * The Snow Wolf can attack the player upon encounter. The player must have a knife
 * in order to defeat the Snow Wolf, otherwise are forced to flee, relocating the 
 * Snow Wolf to a different room.
 * 

 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class SnowWolf extends MovingEntity {
    
        private String knife; // knife required to defeat the Snow Wolf

    /**
     * Initialises a new SnowWolf instance.
     * 
     * @param rooms A list of names of rooms the Snow Wolf can move between.
     * @param knife The item required to defeat the Snow Wolf.
     */
    public SnowWolf(List<String> rooms, String knife) {
        super("snow-wolf", rooms);
        this.knife = knife;
    }

    // Public methods

    /**
     * Handles an encounter between the player and the Snow Wolf. If the player has
     * the knife, they defeat the Snow Wolf. Otherwise, the player is forced to flee
     * and the Snow Wolf relocates to a different room.
     * 
     * @param itemHandler The ItemHandler interface managing the player's inventory.
     * @param roomHandler The RoomHandler interface managing room interactions.
     * @return true if the Snow Wolf is defeated, false if the player is forced to flee.
     */
    public boolean encounter(ItemHandler itemHandler, RoomHandler roomHandler) {
        System.out.println("The Snow Wolf growls menacingly. It is ready to attack!");
        Game.delay(1000); // Adds a short delay for dramatic effect

        if (itemHandler.hasItem(knife)) { // Checks if the player has the required item
            System.out.println("The Snow Wolf attacks, but you fight back with your knife!");
            System.out.println("You kill the Snow Wolf! It will no longer threaten you.");
            Game.delay(1000);
            return true; // indicates the Snow Wolf was defeated
        } else {
            System.out.println("You have no weapon! The Snow Wolf forces you to flee!");
            move(roomHandler); // moves the Snow Wolf to a different room as
                               //defined by roomHandler's logic
            return false; // indicates the player fled
        }
    }

    /**
     * Provides an action when the entity is examined.
     */
    @Override
    public void examine() {
        // No specific action for the Snow Wolf
    }
}
