
/**
 * Write a description of class Entity here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Entity {
    private String name;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void examine();
    
}
