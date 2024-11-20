
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
    
    public Unlockable(String name, String description, Item requiredItem) {
        super(name, description);
        this.isUnlocked = false;
        this.requiredItem = requiredItem;
    }
    
    @Override
    public void interact(EntityHandler handler)
    {
        if (isUnlocked) {
            handler.handleInteraction(this, true);
        } else {
            handler.handleInteraction(this, false);
        }
    }
    
    public Item getRequiredItem()
    {
        return requiredItem;
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
