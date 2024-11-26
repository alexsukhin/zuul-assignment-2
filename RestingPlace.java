
/**
 * Write a description of class restingPlace here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RestingPlace extends Unlockable
{
    private int numRests; // For our fireplace functionality, limits number of uses to two
    private boolean fullyRestoresHeat; // True for cave (fully restores heat), false for fireplace (partially restores heat )
    
    public RestingPlace(String name, Item requiredItem, String lockedMessage, String unlockedMessage, String useItemMessage, boolean fullyRestoresHeat) {
        super(name, requiredItem, lockedMessage, unlockedMessage, useItemMessage, requiredItem != null);  // If an item is required to unlock (for fireplace)
        
        this.numRests = fullyRestoresHeat ? 0 : 2;  // If it's a cave, no limits (no numRests for cave)
        this.fullyRestoresHeat = fullyRestoresHeat;
    }
    
    public void rest(GameState gameState)
    {
        if (fullyRestoresHeat()) {
            System.out.println("You rest in the cave and feel fully rejuvenated.");
            gameState.adjustHeat(gameState.getMaxHeat());
        } else if (getNumRests() > 0) {
            System.out.println("You rest by the fireplace and regain some warmth.");
            gameState.adjustHeat(20);
            decrementRests();
        } else {
            System.out.println("The fireplace breaks. You cannot rest anymore!");
            lock();
        }
        
        System.out.println(gameState.displayHeat());
        
    }
    
    public boolean fullyRestoresHeat()
    {
        return fullyRestoresHeat;
    }
    
    public void decrementRests()
    {
        if (numRests > 0) {
            numRests--;
        }
    }
    
    public int getNumRests() {
        return numRests;
    }
    
}
