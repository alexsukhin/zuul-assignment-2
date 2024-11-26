import java.util.List;
import java.util.Random;

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
    
    public void move(GameState gameState)
    {
        Room currentRoom = rooms.get(currentRoomIndex);
        Room playerRoom = gameState.getPreviousRoom();
        Random random = new Random();
        int attempts = 100;
        
        while (attempts > 0) {
            int randomRoomIndex = random.nextInt(rooms.size());
            Room nextRoom = rooms.get(randomRoomIndex);
            
            if (randomRoomIndex != currentRoomIndex && canMoveTo(nextRoom, playerRoom)) {
                currentRoomIndex = randomRoomIndex;
                
                currentRoom.removeEntity(this.getName());
                nextRoom.addEntity(this);
                
                return;
            }
    
            attempts--;
        }
        
        System.out.println(getName() + " cannot find a suitable room to move to.");
    }
    
    public abstract boolean canMoveTo(Room targetRoom, Room playerRoom);
    
    @Override
    public abstract void examine();
    
}
