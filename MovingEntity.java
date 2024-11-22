import java.util.List;

/**
 * Write a description of class MovingEntity here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class MovingEntity extends Entity
{
    private List<Room> rooms;
    private int currentRoomIndex;
    
    public MovingEntity(String name, List<Room> rooms)
    {
        super(name);
        this.rooms = rooms;
        this.currentRoomIndex = 0;
    }
    
    public Room getCurrentRoom()
    {
        return rooms.get(currentRoomIndex);
    }
    
    public void move()
    {
        currentRoomIndex = (currentRoomIndex + 1) % rooms.size();
    }
    
    @Override
    public abstract void examine(GameState gameState);

    @Override
    public abstract void interact(GameState gameState, Item item);

}
