import java.util.List;
import java.util.Random;

/**
 * This class represents an entity that can move between different rooms
 * within the game world. The entity's movement is determined by a random
 * function and whether the entity should avoid the player's current room.
 *
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class MovingEntity extends Entity {
    
    private List<String> rooms;       // list of room names the entity can move between
    private int currentRoomIndex;    // index of the current room within the list of rooms

    /**
     * Initialises a new MovingEntity with a name and a list of accessible rooms.
     * 
     * @param name The name of the moving entity.
     * @param rooms A list of room names the entity can move between.
     */
    public MovingEntity(String name, List<String> rooms) {
        super(name);
        this.rooms = rooms;
        this.currentRoomIndex = 0; // starts in the first room by default
    }

    // Getter methods
    
    /**
     * @return The name of the current room.
     */
    public String getCurrentRoom() {
        return rooms.get(currentRoomIndex);
    }

    // Public methods
    
    /**
     * Attempts to move the entity to a new room. The movement is determined
     * by a random function. The function avoids returning to the player's room
     * if the entity is a SnowWolf as defined by the RoomHandler's logic within
     * gameState.
     * 
     * @param roomHandler The RoomHandler interface that manages room interactions and constraints.
     */
    public void move(RoomHandler roomHandler) {
        String currentRoom = rooms.get(currentRoomIndex);
        String playerRoom = roomHandler.getPreviousRoom(); // retrieves the player's last room
        Random random = new Random();
        int attempts = 100; // there is a possibility of our random function to continuously
                            // choose the same index, so we set attempts to a large number to
                            // prevent breaking out of loop without picking a room to go to 

        while (attempts > 0) {
            int randomRoomIndex = random.nextInt(rooms.size()); // randomly selects an index
            String nextRoom = rooms.get(randomRoomIndex); // gets the name of room of the randomly chosen index
            
            // Checks if the selected room is different from the current room and is valid according to the RoomHandler
            if (randomRoomIndex != currentRoomIndex && roomHandler.canMoveTo(this, nextRoom, playerRoom)) {
                currentRoomIndex = randomRoomIndex; // updates the current room index
                roomHandler.moveEntity(this, currentRoom, nextRoom); // moves the entity to a different room as defined by
                                                                     // the roomHandler's logic within gameState.                             
                return;
            }

            attempts--; // decrements the number of remaining attempts
        }

        System.out.println(getName() + " cannot find a suitable room to move to.");
    }

    /**
     * Provides an action when the entity is examined.
     */
    @Override
    public void examine() {
        // No specific action for the MovingEntity
    }
}