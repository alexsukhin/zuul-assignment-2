/**
 * This class represents an entity in the game world which the player may
 * unlock with a specific item. The object may be in a locked or unlocked
 * state, and will provide different messages based on its state. This class
 * handles the logic for unlocking this object and whether the player's item
 * should be deleted on use.
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class Unlockable extends Entity {
    
    private String requiredItem;       // stores the required item in order to unlock this entity
    private String lockedMessage;      // message shown when the object is locked
    private String unlockedMessage;    // message shown when the object is unlocked
    private String useItemMessage;     // message shown when the required item is used
    private boolean consumeItem;       // represents whether the item used to unlock should be consumed
    private boolean isUnlocked;
       
    /**
     * Initialises a new unlockable object
     * 
     * @param name The name of the Unlockable object.
     * @param requiredItem The name of the item required to unlock the entity.
     * @param lockedMessage The message displayed when the object is locked.
     * @param unlockedMessage The message displayed when the object is unlocked.
     * @param useItemMessage The message displayed when the required item is used on the object.
     * @param consumeItem A boolean indicating whether the used item should be deleted on use.
     */
    public Unlockable(String name, String requiredItem, String lockedMessage, String unlockedMessage, String useItemMessage, boolean consumeItem) {
        super(name);
        this.requiredItem = requiredItem;
        this.lockedMessage = lockedMessage;
        this.unlockedMessage = unlockedMessage;
        this.useItemMessage = useItemMessage;
        this.consumeItem = consumeItem;
        this.isUnlocked = (requiredItem == null); // automatically unlocked if no item required
    }

    // Getter methods
    
    /**
     * Checks whether the entity is unlocked or not.
     * @return true if the entity is unlocked, false if it isn't.
     */
    public boolean isUnlocked() {
        return isUnlocked;
    }

    /**
     * @return The name of the required item.
     */
    public String getRequiredItem() {
        return requiredItem;
    }

    /**
     * @return The message represented on an unlocked state.
     */
    public String getUnlockedMessage() {
        return unlockedMessage;
    }

    /**
     * @return The message represented on a locked state.
     */
    public String getLockedMessage() {
        return lockedMessage;
    }

    /**
     * @return The message represented when the required item is used on the entity.
     */
    public String getUseItemMessage() {
        return useItemMessage;
    }

    // Public methods
    
    /**
     * Attempts to unlock the entity using the required item.
     * If the object is already unlocked, it displays the unlocked message.
     * If the item matches the required item, the object is unlocked, and the item
     * is removed from the inventory if it should be consumed.
     * 
     * @param itemHandler The interface responsible for managing the player's inventory.
     * @param itemName The name of the item the player is using to unlock the entity.
     */
    public void tryUnlock(ItemHandler itemHandler, String itemName) {
        if (isUnlocked()) {
            System.out.println(getUnlockedMessage());
            return; // returns out of function as object is already unlocked
        }
        
        if (itemName.equals(getRequiredItem())) {
            unlock();
            System.out.println(getUseItemMessage());
            
            if (shouldConsumeItem()) {
                itemHandler.removeItem(itemName); // removes the required item from the player's inventory
            }
        } else {
            System.out.println("That item doesn't work here.");
        }
    }

    /**
     * If the entity is unlocked, displays the unlocked message, else
     * displays the locked message.
     */
    @Override
    public void examine() {
        if (isUnlocked) {
            System.out.println(unlockedMessage);
        } else {
            System.out.println(lockedMessage);
        }
    }
    
    /**
     * Locks the object
     */
    public void lock() {
        isUnlocked = false;
    }
    
    // Helper methods
    
    /**
     * @return true if the item should be consumed, false otherwise
     */
    private boolean shouldConsumeItem() {
        return consumeItem;
    }
    
    /**
     * Unlocks the object
     */
    private void unlock() {
        isUnlocked = true;
    }
}