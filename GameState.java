
/**
 * Write a description of class GameState here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameState
{
    private Room currentRoom;
    private Inventory inventory;
    
    public GameState(Room startingRoom) {
        this.currentRoom = startingRoom;
        inventory = new Inventory(6);
    }
    
    
    public void setCurrentRoom(Room room)
    {
        this.currentRoom = room;
    }
    
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    public Inventory getInventory()
    {
        return inventory;
    }
}
