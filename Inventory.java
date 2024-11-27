import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * This class represents the player's inventory, holding items that can be used, inspected
 * or removed. Handles adding adding and removes items, and displaying the total weight of all
 * items and checking whether an item is present or not
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class Inventory implements ItemHandler
{

    private List<Item> items; // list of item names represented as inventory
    private HashMap<String, Item> itemCatalog; // map containing all available items in the game, each uniquely identified 
    private int maxWeight; // maximum weight of the inventory in kilograms

    /**
     * Initialises the player's inventory with an item catalogue containing
     * all available items in the game
     * 
     * @param itemCatalog A hashmap containing all the available items in the game,
     *                    where each item is uniquely identified by a name
     */
    public Inventory(HashMap<String, Item> itemCatalog)
    {
        this.items = new ArrayList<>();
        this.itemCatalog = itemCatalog;
        maxWeight = 20;
    }

    // Getter methods

    /**
     * Retrieves an item from the inventory by its name
     * 
     * @param itemName The name of the item to retrieve.
     * @return Gets the item if found, otherwise null.
     */
    @Override
    public Item getItem(String itemName)
    {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;  // returns null if the item is not found
    }

    /**
     * Check if the inventory contains an item with the specified name.
     * 
     * @param itemName The name of the item to check.
     * @return true if the item is in the inventory, otherwise false.
     */
    @Override
    public boolean hasItem(String itemName)
    {
        return getItem(itemName) != null;
    }

    /**
     * @return The total weight of items in kilograms.
     */
    public int getTotalWeight()
    {
        int totalWeight = 0;
        for (Item item : items) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }
    
    /**
     * @return The max weight of the inventory in kilograms.
     */
    public int getMaxWeight()
    {
        return maxWeight;
    }

    // Public methods

    /**
     * Adds an item to the inventory by its name.
     * 
     * @param itemName The name of the item to add to the inventory.
     */
    @Override
    public void addItem(String itemName)
    {
        Item item = itemCatalog.get(itemName);

        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null item to the inventory");
        }

        items.add(item);
    }

    /**
     * Removes an item from the inventory by its name.
     * 
     * @param itemName The name of the item to remove.
     * @return true if the item was successfully removed, otherwise false.
     */
    @Override
    public boolean removeItem(String itemName)
    {
        Item item = getItem(itemName);
        return item != null && removeItem(item);  // removes item if found
    }

    /**
     * Display all the items in the inventory if it is not empty.
     * If the inventory is empty, tells the player.
     */
    public void showInventory()
    {
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Your inventory contains:");
            for (Item item : items) {
                System.out.println(item.getItemDetails());
            }
        }
    }

    // Helper methods

    /**
     * Removes an item from the inventory using Item class, to be used
     * in removing an item using a String.
     * 
     * @param The item to remove.
     * @return true if the item was successfully removed, otherwise false.
     */
    private boolean removeItem(Item item)
    {
        return items.remove(item);  // Helper method to remove the item from the list
    }
}
