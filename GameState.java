
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
    
    public GameState(Room startingRoom) {
        this.currentRoom = startingRoom;
        inventory = new Inventory();
    }
    
    public boolean canPickUp(Item item)
    {
        return inventory.getTotalWeight() + item.getWeight() <= maxWeight;
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

