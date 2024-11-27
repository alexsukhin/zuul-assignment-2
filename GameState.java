import java.util.HashMap;
import java.util.Stack;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

/**
 * This class represents the game state that managess the player's interactions
 * with rooms, entities and items within the game. It handles the current room,
 * the player's inventory, heat levels, entity encounters and movement between
 * rooms.
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class GameState implements RoomHandler
{

    private Room currentRoom;            // room the player is currently in
    private Room endingRoom;             // final room in the game
    private HashMap<String, Room> rooms;       // hashmap of all rooms in the game world
    private Stack<Room> previousRooms;        // stack to keep track of previous rooms visited
    private HashMap<String, Entity> entities;  // hashmap of all entities in the game world
    
    private Inventory inventory;           // player's inventory
    
    private int currentHeat;         // the currentheat level of the player
    private int maxHeat;             // the maximum heat level the player can have
    
    /**
     * Initialises the game state with rooms, items, and entities.
     * 
     * @param rooms A hashmap of rooms in the game.
     * @param items A hashmap of items available in the game.
     * @param entities A hashmap of entities present in the game.
     */
    public GameState(HashMap<String, Room> rooms, HashMap<String, Item> items, HashMap<String, Entity> entities)
    {
        this.currentRoom = rooms.get("wreck"); // sets the starting room
        this.endingRoom = rooms.get("rescue"); // sets the ending room
        this.rooms = rooms;
        this.previousRooms = new Stack<>();
        this.entities = entities;
        this.inventory = new Inventory(items); // initialises the player's inventory
        this.currentHeat = 75; // heat at 75 forces player to rest at fireplace
        this.maxHeat = 100;
    }

    // Getter methods

    /**
     * @return The current room.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Gets a specific room by its name.
     * 
     * @param roomName The name of the room.
     * @return The room with the specified name.
     */
    public Room getRoom(String roomName)
    {
        return rooms.get(roomName);
    }

    /**
     * Gets the previous room name the player was in.
     * 
     * @return The previous room name.
     */
    @Override
    public String getPreviousRoom()
    {
        if (!previousRooms.isEmpty()) {
            return previousRooms.peek().getName().toLowerCase();
        } else {
            return null;
        }
    }

    /**
     * @return The player's inventory.
     */
    public Inventory getInventory()
    {
        return inventory;
    }

    /**
     * Gets the current heat level of the player.
     * 
     * @return The current heat.
     */
    public int getCurrentHeat()
    {
        return currentHeat;
    }

    /**
     * Gets the maximum heat level the player can have.
     * 
     * @return The maximum heat.
     */
    public int getMaxHeat()
    {
        return maxHeat;
    }
    
    // Setter methods

    /**
     * Sets the maximum heat level.
     * 
     * @param maxHeat The new maximum heat level.
     */
    public void setMaxHeat(int maxHeat)
    {
        this.maxHeat = maxHeat;
    }

    /**
     * Sets the current room the player is in.
     * 
     * @param room The new room.
     */
    public void setCurrentRoom(Room room)
    {
        this.currentRoom = room;
    }

    // Public methods
    
    /**
     * Adjusts the player's heat by the given amount, ensuring it stays within valid bounds.
     * 
     * @param heat The amount to adjust the heat by.
     */
    public void adjustHeat(int heat)
    {
        this.currentHeat += heat;
        if (currentHeat >= maxHeat) {
            currentHeat = maxHeat;
        } else if (currentHeat < 0) {
            currentHeat = 0;
        }
    }

    /**
     * Adds the previous room to the stack of previous rooms.
     * 
     * @param previousRoom The room to add to the stack.
     */
    public void addRoomToStack(Room previousRoom)
    {
        previousRooms.push(previousRoom);
    }

    /**
     * Displays the current heat status.
     * 
     * @return A string showing the current and max heat.
     */
    public String displayHeat()
    {
        return "Heat: " + getCurrentHeat() + "/" + getMaxHeat();
    }

    /**
     * Moves the player back to the previous room if possible.
     * 
     * @return true if the player successfully moved back, false if there are no previous rooms.
     */
    public boolean back()
    {
        if (previousRooms.isEmpty()) {
            return false;
        } else {
            currentRoom = previousRooms.pop(); // pops the previous room from the stack
            return true;
        }
    }

    /**
     * Checks if the player can carry a specific item without exceeding the weight limit.
     * 
     * @param item The item to check.
     * @return true if the item can be carried, false otherwise.
     */
    public boolean belowWeightLimit(Item item)
    {
        // Checks if the sum of all item's weight in inventory plus the weight of the
        // item to be added to the inventory is less than the maximum weight allowed
        return inventory.getTotalWeight() + item.getWeight() <= inventory.getMaxWeight();
    }

    /**
     * Handles encounters with entities in the current room.
     * 
     * @param previousRoom The previous room the player was in.
     * @return true if the encounter was handled and the player moved back, false otherwise.
     */
    public boolean handleEntityEncounters(Room previousRoom)
    {
        Room currentRoom = getCurrentRoom();
        Deer deer = (Deer) entities.get("deer");
        
        // Randomly moves the deer to another defined room if it is alive
        if (deer.isAlive()) {
            deer.move(this);
        }
        
        // If we enter a room with a SnowWolf, call the encounter method within snowWolf,
        // handling the encounter between the player and the SnowWolf
        if (currentRoom.hasEntity("snow-wolf")) {
            SnowWolf snowWolf = (SnowWolf) currentRoom.getEntity("snow-wolf");
    
            if (!snowWolf.encounter(inventory, this)) {
                back(); // if we had an unsuccessful encounter, forces the player back to the previous room
                return true;
            } else {    
                currentRoom.removeEntity("snow-wolf"); // if we had a successful encounter, removes the 
                                                       // SnowWolf entity from the room
            }
        }
        
        return false;
    }

    /**
     * Handles the killing of an entity in the game
     * 
     * @param entity The entity being killed.
     * @param currentRoom The room where the entity is located.
     * @param itemName The name of the item used to kill the entity.
     */
    public void handleEntityKill(Entity entity, Room currentRoom, String itemName)
    {
        if (entity instanceof Deer) { // checks whether we the entity is a Deer class
            Deer deer = (Deer) entity;
            // If we try to kill the Deer using an item, calls the killDeer method within
            // our Deer class
            if (deer.killDeer(itemName)) {
                // If we had a successful encounter, adds leather to the player's inventory
                // and removes the deer entity from the current room
                inventory.addItem("leather");
                currentRoom.removeEntity(deer.getName());
                System.out.println(currentRoom.getEntitiesString());
            } else {
                System.out.println("The item doesn't work here.");
            }
        }
    }

    /**
     * Checks if the player can traverse a lake, depending on whether the lake is unlocked.
     * 
     * @param currentRoom The current room the player is in.
     * @param previousRoom The previous room the player was in.
     * @param nextRoom The next room to traverse to.
     * @param entity The entity which relates to the traversal of the lake
     * @return true if the player can traverse the lake, false otherwise.
     */
    public boolean canTraverseLake(Room currentRoom, Room previousRoom, Room nextRoom, Entity entity)
    {
        Unlockable unlockable = (Unlockable) entity;
        
        // This first check allows us to go backwards if we enter a room with a lake entity in it
        // If the previous room is equivalent to the room we want to go towards, allows us to go back
        // by returning true
        if ((nextRoom == previousRoom) || (previousRoom == null)) {
            return true;
        // This second check does not allow us to go to the ahead room if the lake entity is locked
        } else if (!unlockable.isUnlocked()) {
            return false;
        }
        
        // If we get to this point, the lake entity is unlocked, allowing us to travel forwards. We
        // lock the lake entity again, ensuring to go backwards the player must use the required item
        // to traverse the lake again.
        unlockable.lock();
        return true;
    }

    /**
     * Checks if the player has reached the final room in the game.
     * 
     * @return true if the player is in the final room, false otherwise.
     */
    public boolean checkFinalRoom()
    {
        return currentRoom == endingRoom;
    }

    /**
     * Checks if the player has the required item to complete the game.
     * 
     * @return true if the player has the required item, false otherwise.
     */
    public boolean checkFinalItem()
    {
        return inventory.hasItem("repaired-radio");
    }

    /**
     * Teleports the player to a random room.
     * 
     * @param nextRoom The room to teleport to.
     */
    public void teleportRandomRoom(Room nextRoom)
    {
        // This list defines a set of rooms which the player could
        // randomly teleport to.
        List<Room> teleporterRooms = Arrays.asList(
            rooms.get("camp"),
            rooms.get("cave"),
            rooms.get("station"), 
            rooms.get("wreck"),
            rooms.get("cabin"),
            rooms.get("forest")
        );
        
        Room randomRoom = teleporterRooms.get(new Random().nextInt(teleporterRooms.size())); // randomly picks a room
        System.out.println("You step into Storm’s Fury, where the howling winds and blinding snow overwhelm your senses.");
        System.out.println("Disoriented and lost, you wander aimlessly, only to find yourself back in a familiar place, unsure how you returned.");
        Game.delay(1500);
        System.out.println(randomRoom.getLongDescription());
        System.out.println(displayHeat());
        
        currentRoom = randomRoom; // sets the current room to the random room
        reconfigureStack(randomRoom); // reconfigures the stack to the state it was in
                                      // when the user last appeared at this random room
        nextRoom.unsetTeleporter(); // implies that this room does not allow future
                                    // random teleportations
        Game.delay(1500);
    }

    /**
     * Reconfigures the stack of previous rooms after a teleportation.
     * 
     * @param room The new room to set as the next room of the last room on the top of the stack.
     */
    public void reconfigureStack(Room room)
    {   
        // Reconfigures the stack so that the last room in the stack is the new
        // room defined in the parameter
        while (!previousRooms.isEmpty() && previousRooms.peek() != room) {
            previousRooms.pop();
        }
        // Ensures that the room defined in the parameter is not in the stack, so that the
        // stack represents the room one before the new room to ensure the correct order 
        previousRooms.pop();
    }

    // Helper methods

    /**
     * Checks if an entity can move to the same room as the player
     * 
     * @param movingEntity The entity attempting to move.
     * @param targetRoom The target room to move to.
     * @param playerRoom The room the player is currently in.
     * @return true if the entity can move to the player's room, false otherwise.
     */
    @Override
    public boolean canMoveTo(MovingEntity movingEntity, String targetRoom, String playerRoom)
    {
        if (movingEntity instanceof Deer) {
            return true; // deers can always move to any room even if a player is there
        } else if (movingEntity instanceof SnowWolf) {
            return !targetRoom.equals(playerRoom); // SnowWolfs can never move to a room where a
                                                   // player is already there
        }
        return false;
    }

    /**
     * Moves an entity from one room to another.
     * 
     * @param entity The entity to move.
     * @param currentRoomID The ID of the current room.
     * @param nextRoomID The ID of the next room.
     */
    @Override
    public void moveEntity(MovingEntity entity, String currentRoomID, String nextRoomID)
    {
        Room currentEntityRoom = rooms.get(currentRoomID);
        Room nextRoom = rooms.get(nextRoomID);
        
        currentEntityRoom.removeEntity(entity.getName()); // removes the entity from the current room
        nextRoom.addEntity(entity); // adds the entity to the next room
    }
}
