import java.util.Stack;
/**
 * Write a description of class GameState here.
 *I
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameState
{
    private Room currentRoom;
    private Inventory inventory;
    private final int maxWeight = 50;
    private Stack<Room> previousRooms;
    
    public GameState(Room startingRoom) {
        this.currentRoom = startingRoom;
        inventory = new Inventory();
        previousRooms = new Stack<>();
    }
    
    public void addRoomToStack(Room previousRoom)
    {
        previousRooms.push(previousRoom);
    }
    
    public void setCurrentRoom(Room room)
    {
        this.currentRoom = room;
    }
    
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    public Room getPreviousRoom()
    {
        return previousRooms.peek();
    }
    
    public boolean Back()
    {
        if (previousRooms.isEmpty()) {
            return false;
        } else {
            currentRoom = previousRooms.pop();
            return true;
        }

    }
    
    public Inventory getInventory()
    {
        return inventory;
    }
    
    public boolean canPickUp(Item item)
    {
        return inventory.getTotalWeight() + item.getWeight() <= maxWeight;
    }
    
    public boolean handleEntityEncounters(Room previousRoom) {
        Room room = getCurrentRoom();

        if (previousRoom.hasEntity("deer")) {
            Deer deer = (Deer) previousRoom.getEntity("deer");
            deer.move(this);
        }

        if (room.hasEntity("snow-wolf")) {
            SnowWolf snowWolf = (SnowWolf) room.getEntity("snow-wolf");
    
            if (!snowWolf.encounter(this)) {
                Back();
                return true;
            } else {
                room.removeEntity("snow-wolf");
            }
        }
        
        return false;
    }

}

