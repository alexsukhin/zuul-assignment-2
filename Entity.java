/**
 * Abstract class representing a generic entity in the game world.
 * This class provides a basic structure for all entities to follow.
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26  
 */
public abstract class Entity {

    // The name of the entity
    private String name;

    /**
     * Initialises the entity base class.
     * 
     * @param name The name of the entity.
     */
    public Entity(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the entity.
     * 
     * @return The name of the entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Abstract method for examining the entity.
     */
    public abstract void examine();
}