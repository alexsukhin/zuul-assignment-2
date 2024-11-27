/**
 * Write a description of class Item here.
 *
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String name;
    private String description;
    private int weight;
    private boolean pickupable;

    /**
     * Initialises an item.
     * 
     * @param name        The name of the item
     * @param description A short description of the item
     * @param weight      The weight of the item in kilograms
     * @param pickupable  Whether the item can be picked up
     */

    public Item(String name, String description, int weight, boolean pickupable)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.pickupable = pickupable;
    }
    
    //Getter methods
    
    /**
     * Gets the name of the item
     * 
     * @return The name of the item
     */
    public String getName()
    {
        return name.toLowerCase();
    }
    
    /**
     * Gets the description of the item.
     * 
     * @return The description of the item
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Gets the weight of the item.
     * 
     * @return The weight of the item in kilograms
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Gets the details of the item in a formatted string.
     * 
     * @return A string containing the item's name, description, and weight
     */
    public String getItemDetails()
    {
        return name + ": " + description + " (weight: " + weight + "kg)";
    }
    
    /**
     * Checks if the item can be picked up.
     * 
     * @return true if the item can be picked up, false otherwise
     */
    public boolean canPickUp()
    {
        return pickupable;
    }

}
