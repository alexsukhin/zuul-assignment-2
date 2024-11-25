
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
    private String useItemMessage;
    private boolean consumeItem;
    
    public Unlockable(String name, Item requiredItem, String lockedMessage, String unlockedMessage, String useItemMessage, boolean consumeItem) {
        super(name);
        this.isUnlocked = (requiredItem == null);
        this.requiredItem = requiredItem;
        this.lockedMessage = lockedMessage;
        this.unlockedMessage = unlockedMessage;
        this.useItemMessage = useItemMessage;
        this.consumeItem = consumeItem;
    }
    
    @Override
    public void examine(GameState gameState) {
        if (isUnlocked) {
            System.out.println(unlockedMessage);
        } else {
            System.out.println(lockedMessage);
        }
    }

    public void interact(GameState gameState, Item item) {
        if (isUnlocked) {
            System.out.println(unlockedMessage);
        } else if (item != null && item.equals(requiredItem)) {
            isUnlocked = true;
            System.out.println(useItemMessage);
            if (consumeItem && requiredItem != null) {
                gameState.getInventory().removeItem(item);
            }
        } else {
            System.out.println("That item doesn't work here.");
        }
    }
    
    public void rest(GameState gameState)
    {
        return;
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
    
    public void lock()
    {
        isUnlocked = false;
    }
}
