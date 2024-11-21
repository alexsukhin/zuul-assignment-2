
/**
 * Write a description of class Fireplace here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Fireplace extends Unlockable
{
    private boolean hasFire;
    
    public Fireplace(String name, String description, Item requiredItem) {
        super(name, description, requiredItem, 
              "The fireplace is cold and needs firewood to start a fire.", 
              "You put firewood in the fireplace and light it. Warmth spreads around!");
        this.hasFire = false;
    }
    
    @Override
    public void interact(GameState gameState) {
        if (hasFire) {
            System.out.println("The fire is burning brightly, keeping you warm. +5 to heat.");
            // Optionally, adjust player status here
        } else {
            // Use the existing unlocking logic
            Inventory inventory = gameState.getInventory();
            if (inventory.hasItem(getRequiredItem())) {
                unlock(); // This sets `isUnlocked` to true
                inventory.removeItem(getRequiredItem());
                hasFire = true;
                System.out.println(getUnlockedMessage());
            } else {
                System.out.println(getLockedMessage());
            }
        }
    }
    
    public void addFire()
    {
        hasFire = true;
    }
}

