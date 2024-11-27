import java.util.List;

/**
 * This class represents a Deer, a passive moving entity in the game world.
 * The Deer roams between rooms and can be hunted by the player. If the player
 * has a knife, the player may kill the Deer to gain resources.
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class Deer extends MovingEntity
{
    
    private String knife;  // knife required to kill the Deer
    private boolean alive; // tracks whether the Deer is alive

    /**
     * Initialises a new Deer instance.
     *
     * @param rooms A list of names of rooms the Deer can move between.
     * @param knife The name of the item required to kill the Deer.
     */
    public Deer(List<String> rooms, String knife)
    {
        super("deer", rooms);
        this.knife = knife;
        this.alive = true;
    }

    public void setAlive(boolean bool)
    {
        alive = bool;
    }
    
    // Public methods

    /**
     * Attempts to kill the Deer using the specified item. If the player has
     * the knife, the Deer is killed and the player receives leather.
     * 
     * @param itemName The item name the player is using to attack the Deer.
     * @return true if the Deer is successfully killed, false otherwise.
     */
    public boolean killDeer(String itemName)
    {
        if (itemName.equals(knife)) { // checks if the item matches the required item
            System.out.println("You kill the Deer! You gain a piece of leather for your troubles.");
            alive = false; // updates the Deer's status to dead
            return true;
            
            
        } else {
            return false; // fails if the item doesn't match
        }
    }

    /**
     * @return true if the Deer is alive, false otherwise.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Provides an action when the entity is examined.
     */
    @Override
    public void examine()
    {
        System.out.println("The deer seems peaceful, roaming between the rooms.");
    }
}
