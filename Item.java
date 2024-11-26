/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String name;
    private String description;
    private int weight;
    private boolean pickupable;

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, int weight, boolean pickupable)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.pickupable = pickupable;
    }
    
    

    public String getName()
    {
        return name.toLowerCase();
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public String getItemDetails()
    {
        return name + ": " + description + " (weight: " + weight + "kg)";
    }
    
    public boolean canPickUp()
    {
        return pickupable;
    }

}
