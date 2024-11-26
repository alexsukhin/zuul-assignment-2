import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class Inventory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Inventory
{
    private List<Item> items;
    /**
     * Constructor for objects of class Inventory
     */
    public Inventory()
    {
        items = new ArrayList<>();
    }
    
    public int getTotalWeight()
    {
        int totalWeight = 0;
        for (Item item : items) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }
    
    public Item getItem(String itemName)
    {
        //make better implementation
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }
    
    public boolean hasItem(Item item)
    {
        return items.contains(item);
    }
    
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    public boolean removeItem(Item item)
    {
        return items.remove(item);
    }
    
    public void showInventory()
    {
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty");
        } else {
            System.out.println("Your inventory contains:");
            for (Item item : items) {
                System.out.println(item.getItemDetails());
            }
        }
    }
}
