import java.util.Set;
import java.util.HashMap;

/**
 * This class represents a room within the game world. A room represents
 * one location in the scenery of the game. It is connected to other rooms
 * via exits. For each existing exit, the room stores a reference to the
 * neighboring room.
 * 
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class Room {
    private String name;
    private String description;
    private int heat;
    private boolean teleporter;
    private HashMap<String, Room> exits;        // stores exits of this room
    private HashMap<String, Item> items;         // stores items present in the room
    private HashMap<String, Entity> entities;    // stores entities present in the room
        
    /**
     * Initialises a room.
     * 
     * @param name        The name of the room.
     * @param description A short description of the room.
     * @param heat        The heat level of the room.
     */
    public Room(String name, String description, int heat) {
        this.name = name;
        this.description = description;
        this.teleporter = false;  // defaults to not a teleporter room
        this.heat = heat;
        exits = new HashMap<>();
        items = new HashMap<>();
        entities = new HashMap<>();
    }

    /**
     * Overloaded constructor method which initializes a room with a teleporter flag.
     * 
     * @param name        The name of the room
     * @param description A short description of the room
     * @param teleporter  Defines whether the room is a magic teleporter room
     * @param heat        The heat level of the room
     */
    public Room(String name, String description, boolean teleporter, int heat) {
        this.name = name;
        this.description = description;
        this.teleporter = teleporter;
        this.heat = heat;
        exits = new HashMap<>();
        items = new HashMap<>();
        entities = new HashMap<>();
    }

    // Getter methods
    
    /**
     * @return The name of the room.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the short description of the room.
     * 
     * @return The short description of the room.
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Gets the long description of the room, including exits and entities.
     * 
     * @return The full description of the room.
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString() + "\n" + getEntitiesString();
    }

    /**
     * Gets an item from the room by its name.
     * 
     * @param itemName The name of the item to get.
     * @return The item if found, null otherwise.
     */
    public Item getItem(String itemName) {
        return items.get(itemName.toLowerCase());
    }

    /**
     * Gets an entity from the room by its name.
     * 
     * @param entityName The name of the entity to get.
     * @return The entity if found, null otherwise.
     */
    public Entity getEntity(String entityName) {
        return entities.get(entityName.toLowerCase());
    }
    
    /**
     * Gets the exit in the given direction from this room.
     * 
     * @param direction The direction of the exit.
     * @return The room object in the given direction, or null if no room exists there.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    } 
    
    /**
     * @return The heat level of the room.
     */
    public int getHeat() {
        return heat;
    }

    /**
     * @return true if the room has a teleporter, false otherwise.
     */
    public boolean isTeleporter() {
        return teleporter;
    }
    
    /**
     * Gets a string describing the items in the room.
     * 
     * @return A string listing all items in the room.
     */
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

    /**
     * Gets a string describing the entities in the room.
     * 
     * @return A string listing all entities in the room.
     */
    public String getEntitiesString() {
        String returnString = "Entities:";
        Set<String> keys = entities.keySet();

        if (keys.isEmpty()) {
            return "";
        }

        for (String entity : keys) {
            returnString += "\n- [" + entity + "]";
        }

        return returnString;
    }
    
    //Setter methods

    /**
     * Sets the room's teleporter status to false.
     */
    public void unsetTeleporter() 
    {
        teleporter = false;
    }

    // Public methods

    /**
     * Define an exit from this room in a given direction.
     * 
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Adds an item to the room.
     * 
     * @param item The item to add to the room.
     */
    public void addItem(Item item) {
        items.put(item.getName().toLowerCase().strip(), item);
    }

    /**
     * Adds an entity to the room.
     * 
     * @param entity The entity to add to the room.
     */
    public void addEntity(Entity entity) {
        entities.put(entity.getName().toLowerCase().strip(), entity);
    }

    /**
     * Removes an item from the room by its name.
     * 
     * @param itemName The name of the item to remove.
     */
    public void removeItem(String itemName) {
        items.remove(itemName.toLowerCase());
    }

    /**
     * Removes an entity from the room by its name.
     * 
     * @param entityName The name of the entity to remove.
     */
    public void removeEntity(String entityName) {
        entities.remove(entityName.toLowerCase());
    }

    /**
     * Checks if the room contains an item with the given name.
     * 
     * @param itemName The name of the item to check.
     * @return true if the room contains the item, false otherwise.
     */
    public boolean hasItem(String itemName) {
        return items.containsKey(itemName.toLowerCase());
    }

    /**
     * Checks if the room contains an entity with the given name.
     * 
     * @param entityName The name of the entity to check.
     * @return true if the room contains the entity, false otherwise.
     */
    public boolean hasEntity(String entityName) {
        return entities.containsKey(entityName.toLowerCase());
    }

    // Helper methods

    /**
     * Gets the string describing the exits of the room.
     * 
     * @return A string listing all exits of the room.
     */
    private String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += "\n- " + exit + ": " + exits.get(exit).getName();
        }
        return returnString;
    }


}
