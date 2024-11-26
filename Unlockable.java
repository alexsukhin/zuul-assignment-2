
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
        this.requiredItem = requiredItem;
        this.lockedMessage = lockedMessage;
        this.unlockedMessage = unlockedMessage;
        this.useItemMessage = useItemMessage;
        this.consumeItem = consumeItem;
        
        this.isUnlocked = (requiredItem == null);
    }
    
    public void tryUnlock(Inventory inventory, Item item)
    {
        if (isUnlocked()) {
            System.out.println(getUnlockedMessage());
            return;
        }
        
        if (item != null && item.equals(getRequiredItem())) {
            unlock();
            System.out.println(getUseItemMessage());
            
            if (shouldConsumeItem()) {
                inventory.removeItem(item);                
            }
        } else {
            System.out.println("That item doesn't work here.");
        }
    }
    
    @Override
    public void examine()
    {
        if (isUnlocked) {
            System.out.println(unlockedMessage);
        } else {
            System.out.println(lockedMessage);
        }
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
    
    public String getUseItemMessage()
    {
        return useItemMessage;
    }
    
    public boolean shouldConsumeItem()
    {
        return consumeItem;
    }
}
