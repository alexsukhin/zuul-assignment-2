/**
 * This interface defines methods for handling items in an inventory.
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public interface ItemHandler
{

    /**
     * Retrieves an item from the inventory by its name
     * 
     * @param itemName The name of the item to retrieve.
     * @return Gets the item if found, otherwise null.
     */
    Item getItem(String itemName);

    /**
     * Removes an item from the inventory by its name.
     * 
     * @param itemName The name of the item to remove.
     * @return true if the item was successfully removed, otherwise false.
     */
    boolean removeItem(String itemName);

    /**
     * Check if the inventory contains an item with the specified name.
     * 
     * @param itemName The name of the item to check.
     * @return true if the item is in the inventory, otherwise false.
     */
    boolean hasItem(String itemName);

    /**
     * Adds an item to the inventory by its name.
     * 
     * @param itemName The name of the item to add to the inventory.
     */
    void addItem(String itemName);
}
