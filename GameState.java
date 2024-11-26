import java.util.Stack;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.Arrays;

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
    private Entity deer;
    private HashMap<String, Room> rooms;
    private Stack<Room> previousRooms;
    
    private Inventory inventory;
    private HashMap<String, Item> items;
    
    private int currentHeat = 100;
    private int maxHeat = 100;
    private final int maxWeight = 50;
    
    public GameState(HashMap<String, Room> rooms, HashMap<String, Item> items, Entity entity) {
        this.currentRoom = rooms.get("wreck");
        this.endingRoom = rooms.get("rescue");
        this.deer = entity;
        this.rooms = rooms;
        this.previousRooms = new Stack<>();
        this.inventory = new Inventory();
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
    
    public HashMap<String, Room> getRooms()
    {
        return rooms;
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
        this.currentHeat += heat;
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
    
    public boolean back()
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
        Deer deer = (Deer) this.deer;  
        
        if (deer.isAlive()) {
            deer.move(this);
        }

        if (currentRoom.hasEntity("snow-wolf")) {
            SnowWolf snowWolf = (SnowWolf) currentRoom.getEntity("snow-wolf");
    
            if (!snowWolf.encounter(this)) {
                back();
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
        return currentRoom == endingRoom;
    }
    
    public boolean checkFinalItem()
    {
        return inventory.hasItem(items.get("repaired-radio"));
    }
    
    public void teleportRandomRoom(Room nextRoom)
    {
            List<Room> teleporterRooms = Arrays.asList(
                rooms.get("camp"),
                rooms.get("cave"),
                rooms.get("station"), 
                rooms.get("plane"),
                rooms.get("cabin"),
                rooms.get("forest")
            );
            
            Room randomRoom = teleporterRooms.get(new Random().nextInt(teleporterRooms.size()));
            System.out.println("You step into Storm’s Fury, where the howling winds and blinding snow overwhelm your senses.");
            System.out.println("Disoriented and lost, you wander aimlessly, only to find yourself back in a familiar place, unsure how you returned.");
            System.out.println(randomRoom.getLongDescription());
            System.out.println(displayHeat());
            
            currentRoom = randomRoom;
            reconfigureStack(randomRoom);
            nextRoom.unsetTeleporter();
            Game.delay(1500);
    }
    
    public void reconfigureStack(Room room)
    {   
        while (!previousRooms.isEmpty() && previousRooms.peek() != room) {
            previousRooms.pop();
        }
    }
}