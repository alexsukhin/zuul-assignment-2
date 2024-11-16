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
    private int maxWeight;

    /**
     * Constructor for objects of class Inventory
     */
    public Inventory(int maxWeight)
    {
        items = new ArrayList<>();
        this.maxWeight = maxWeight;
    }
    
    public boolean addItem(Item item)
    {
        if (getTotalWeight() + item.getWeight() > maxWeight) {
            System.out.println("Cannot add item. Max weight exceeded");
            return false;
        }
        items.add(item);
        return true;
    }
    
    public boolean removeItem(Item item)
    {
        return items.remove(item);
    }
    
    public boolean hasItem(Item item)
    {
        return items.contains(item);
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
    
    public int getTotalWeight()
    {
        int totalWeight = 0;
        for (Item item : items) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }
    
    public int getMaxWeight()
    {
        return maxWeight;
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
    
    public boolean isFull() 
    {
        return getTotalWeight() >= maxWeight;
    }
    
}
