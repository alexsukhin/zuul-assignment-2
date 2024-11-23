import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String name;
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Item> items;
    private HashMap<String, Entity> entities;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String name, String description) 
    {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
        entities = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    public void addItem(Item item)
    {
        items.put(item.getName().toLowerCase().strip(), item);
    }
    
    public void addEntity(Entity entity)
    {
        entities.put(entity.getName().toLowerCase().strip(), entity);
    }
    
    public void removeItem(String itemName)
    {
        items.remove(itemName.toLowerCase());
    }
    
    public void removeEntity(String entityName)
    {
        entities.remove(entityName.toLowerCase());
    }
    
    public boolean hasItem(String itemName)
    {   
        return items.containsKey(itemName.toLowerCase());
    }
    
    public boolean hasEntity(String itemName)
    {
        return entities.containsKey(itemName.toLowerCase());
    }
    
    
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {;
        return "You are " + description + ".\n" + getExitString() + "\n" + getEntitiesString();
    }
    
    public String getName()
    {
        return name;
    }
    

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += "\n- " + exit + ": " + exits.get(exit).getName();
        }
        return returnString;
    }
    
    public String getItemString() {
        String returnString = "";
        if (!items.isEmpty()) {
            returnString += "You notice the following items:\n";
            Set<String> keys = items.keySet();
            for (String item : keys) {
                returnString += "- [" + item + "]\n";
            }
        }
        return returnString;
    }
    
    public String getEntitiesString()
    {
        String returnString = "Entities:";
        Set<String> keys = entities.keySet();
        
        if (keys.isEmpty()) {
            return "";
        }
        
        for (String entity : keys) {
            returnString += "\n- [" + entity + "]"; // Use formatting as needed (e.g., bold in markdown)
        }
        
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    public Item getItem(String itemName)
    {
        return items.get(itemName.toLowerCase());
    }
    
    public Entity getEntity(String entityName)
    {
        return entities.get(entityName.toLowerCase());
    }
}

