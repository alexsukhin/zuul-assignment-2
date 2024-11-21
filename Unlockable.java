
/**
 * Write a description of class Unlockable here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Unlockable extends Entity
{
    // instance variables - replace the example below with your own
    private boolean isUnlocked;
    private Item requiredItem;
    private String lockedMessage;
    private String unlockedMessage;
    
    public Unlockable(String name, String description, Item requiredItem, String lockedMessage, String unlockedMessage) {
        super(name, description);
        this.isUnlocked = false;
        this.requiredItem = requiredItem;
        this.lockedMessage = lockedMessage;
        this.unlockedMessage = unlockedMessage;
    }
    
    @Override
    public void interact(GameState gameState)
    {
        Inventory inventory = gameState.getInventory();
        if (isUnlocked()) {
            System.out.println(unlockedMessage);
        } else if (inventory.hasItem(requiredItem)) {
            unlock();
            System.out.println(unlockedMessage);
        } else {
            System.out.println(lockedMessage);
        }
    }
    
    public Item getRequiredItem()
    {
        return requiredItem;
    }
    
    public String getUnlockedMessage()
    {
        return unlockedMessage;
    }
    
    public String getLockedMessage()
    {
        return lockedMessage;
    }
    
    public boolean isUnlocked()
    {
        return isUnlocked;
    }
    
    public void unlock()
    {
        isUnlocked = true;
    }
}
