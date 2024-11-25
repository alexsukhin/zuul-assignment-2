import java.util.Stack;
import java.util.HashMap;

/**
 * Write a description of class GameState here.
 *I
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameState
{
    private Room currentRoom;
    private Room endingRoom;
    private Stack<Room> previousRooms;
    
    private Inventory inventory;
    private HashMap<String, Item> items;
    
    private int currentHeat = 100;
    private int maxHeat = 100;
    private final int maxWeight = 50;
    
    public GameState(Room startingRoom, Room endingRoom, HashMap<String, Item> items) {
        this.currentRoom = startingRoom;
        this.endingRoom = endingRoom;
        previousRooms = new Stack<>();
        inventory = new Inventory();
        this.items = items;
    }
    
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    public Room getPreviousRoom()
    {
        if (!previousRooms.isEmpty()) {
            return previousRooms.peek();
        } else {
            return null;
        }
    }
    
    public Inventory getInventory()
    {
        return inventory;
    }
    
    public HashMap<String, Item> getItems()
    {
        return items;
    }
    
    public int getCurrentHeat()
    {
        return currentHeat;
    }
    
    public int getMaxHeat()
    {
        return maxHeat;
    }
    
    public void adjustHeat(int heat)
    {
        currentHeat += heat;
        if (currentHeat >= maxHeat) {
            currentHeat = maxHeat;
        } else if (currentHeat < 0) {
            currentHeat = 0;
        }
    }
    
    public void setMaxHeat(int maxHeat)
    {
        this.maxHeat = maxHeat;
    }
    
    public void setCurrentRoom(Room room)
    {
        this.currentRoom = room;
    }
        
    public void addRoomToStack(Room previousRoom)
    {
        previousRooms.push(previousRoom);
    } 
    
    public String displayHeat()
    {
        return "Heat: "+ getCurrentHeat() + "/" + getMaxHeat();
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
    
    public boolean belowWeightLimit(Item item)
    {
        return inventory.getTotalWeight() + item.getWeight() <= maxWeight;
    }
    
    public boolean handleEntityEncounters(Room previousRoom) {
        Room currentRoom = getCurrentRoom();

        if (previousRoom.hasEntity("deer")) {
            Deer deer = (Deer) previousRoom.getEntity("deer");
            deer.move(this);
        }

        if (currentRoom.hasEntity("snow-wolf")) {
            SnowWolf snowWolf = (SnowWolf) currentRoom.getEntity("snow-wolf");
    
            if (!snowWolf.encounter(this)) {
                Back();
                return true;
            } else {    
                currentRoom.removeEntity("snow-wolf");
            }
        }
        
        return false;
    }
    
    public void handleEntityKill(Entity entity, Room currentRoom, Item item)
    {
        if (entity instanceof Deer) {
            Deer deer = (Deer) entity;
            if (deer.killDeer(this, item)) {
                inventory.addItem(items.get("leather"));
                currentRoom.removeEntity("deer");
            } else {
                System.out.println("The item doesn't work here.");
            }
        }
    }
    
    public boolean canTraverseLake(Room currentRoom, Room previousRoom, Room nextRoom, Entity entity)
    {
        
        Unlockable unlockable = (Unlockable) entity;
        
        if ((nextRoom == previousRoom) || (previousRoom == null)) {
            return true;
        } else if (!unlockable.isUnlocked()) {
            return false;
        }
        
        unlockable.lock();
        return true;
    }
    
    public boolean checkFinalRoom()
    {
        if (currentRoom == endingRoom) {
            return true;
        } else {
            return false;
        }
    
    }
    
    public boolean checkFinalItem()
    {
        Item repairedRadio = inventory.getItem("repaired-radio");
        
        if (inventory.hasItem(repairedRadio)) {
            return true;
        } else {
            return false;
        }
    }

}




