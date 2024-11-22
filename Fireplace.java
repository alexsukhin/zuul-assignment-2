
/**
 * Write a description of class Fireplace here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Fireplace extends Unlockable
{
    public Fireplace(String name, Item requiredItem) {
        super(name, requiredItem, 
              "The fireplace is cold and needs firewood to start a fire.", 
              "You put firewood in the fireplace and light it. Warmth spreads around!");
    }
    
    @Override
    public void examine(GameState gameState) {
        if (isUnlocked()) {
            System.out.println("The fire is burning brightly, keeping you warm.");
        } else {
            System.out.println(getLockedMessage());
        }
    }
    
    @Override
    public void interact(GameState gameState, Item item) {
        if (isUnlocked()) {
            System.out.println("The fire is already burning brightly.");
            return;
        }

        if (item.equals(getRequiredItem())) {
            unlock();
            gameState.getInventory().removeItem(item);
            System.out.println(getUnlockedMessage());
        } else {
            System.out.println("The " + item.getName() + " cannot be used on the fireplace.");
        }
    }
}

