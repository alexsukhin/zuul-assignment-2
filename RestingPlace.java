/**
 * This class represents a resting place in the game world where the player may 
 * regain warmth. A resting place can either be a fireplace with two uses or a
 * cave with unlimited uses. This class extends Unlockable as the player must
 * use firewood on the fireplace in order to unlock its functionality.
 *
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class RestingPlace extends Unlockable
{
    
    private int numRests;             // limits the number of uses for a fireplace
    private boolean fullyRestoresHeat;

    /**
     * Initialises a new RestingPlace instance.
     * 
     * @param name The name of the resting place.
     * @param requiredItem The item required to unlock the resting place if required.
     *                     If an item is not required it will be represented as null.
     * @param lockedMessage The message shown when the object is locked.
     * @param unlockedMessage The message shown when the object is unlocked.
     * @param useItemMessage The message displayed when the required item is used on the object.
     * @param fullyRestoresHeat A boolean indicating whether this resting place fully restores heat.
     */
    public RestingPlace(String name, String requiredItem, String lockedMessage, String unlockedMessage, String useItemMessage, boolean fullyRestoresHeat)
    {
        super(name, requiredItem, lockedMessage, unlockedMessage, useItemMessage, requiredItem != null); 
        this.fullyRestoresHeat = fullyRestoresHeat; // represented as true for caves (fully restores heat)
                                                    // and false for fireplaces (partial heat restoration)
        this.numRests = fullyRestoresHeat ? 0 : 2; // caves have unlimited uses, fireplaces have two uses
    }

    // Getter methods

    /**
     * @return The number of remaining uses for the resting place
     */
    public int getNumRests()
    {
        return numRests;
    }
    
    /**
     * @return True if the resting place fully restores heat, false otherwise
     */
    public boolean fullyRestoresHeat()
    {
        return fullyRestoresHeat;
    }

    // Public methods
    
    /**
     * Allows the player to rest and regain warmth. Fully restores heat if the 
     * resting place is a cave; partially restores heat if it is a fireplace.
     * Fireplaces have limited uses, and after depletion, they are locked.
     * 
     * @param gameState The current state of the game, used to adjust the player's current heat level
     */
    public void rest(GameState gameState)
    {
        if (isUnlocked()) {
            if (fullyRestoresHeat()) {
                System.out.println("You rest in the cave and feel fully rejuvenated.");
                gameState.adjustHeat(gameState.getMaxHeat()); // restores to maximum heat
            } else if (getNumRests() > 0) {
                System.out.println("You rest by the fireplace and regain some warmth.");
                gameState.adjustHeat(20); // restores heat level by 20
                decrementRests();
            } else {
                System.out.println("The fireplace breaks. You cannot rest anymore!");
                lock(); // locks the fireplace once its uses are exhausted
            }
            System.out.println(gameState.displayHeat()); // displays the player's current heat level
        } else {
            System.out.println(getLockedMessage());
        }
    }

    // Helper methods
    
    /**
     * Decreases the number of remaining uses for the resting place by one, if applicable.
     */
    private void decrementRests()
    {
        if (numRests > 0) {
            numRests--;
        }
    }
}