    /**
 * This interface defines methods for managing the movement of entities
 * within different rooms in the game.
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public interface RoomHandler
{

    /**
     * Get the name of the room the player or moving entity was previously in.
     * 
     * @return The name of the previous room.
     */
    String getPreviousRoom();

    /**
     * Check if the specified entity can move to the target room from the given room.
     * 
     * @param movingEntity The entity trying to move.
     * @param targetRoom   The room the entity is trying to move to.
     * @param playerRoom   The current room the player is in (may be used for validation or specific logic).
     * @return true if the entity can move to the target room, false otherwise.
     */
    boolean canMoveTo(MovingEntity movingEntity, String targetRoom, String playerRoom);

    /**
     * Move an entity from one room to another.
     * 
     * @param entity   The entity that is being moved.
     * @param nextRoom The room the entity will move to.
     */
    void moveEntity(MovingEntity entity, String currentRoom, String nextRoom);
}
