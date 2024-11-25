import java.util.List;

/**
 * Write a description of class SnowWolf here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SnowWolf extends MovingEntity
{
    private Item knife;
    
    public SnowWolf(List<Room> rooms, Item knife)
    {
        super("snow-wolf", rooms);
        this.knife = knife;
    }
    
    public boolean encounter(GameState gameState)
    {
        Inventory inventory = gameState.getInventory();
        
        System.out.println("The Snow Wolf growls menacingly. It is ready to attack!");
        
        Game.delay(1000);
        
        if (inventory.hasItem(knife)) {
            System.out.println("The Snow Wolf attacks, but you fight back with your knife!");             
            System.out.println("You kill the Snow Wolf! It will no longer threaten you.");
             
            Game.delay(1000);
             
            return true;
        } else {
            System.out.println("You have no weapon! The Snow Wolf forces you to flee!");
            move(gameState);
            return false;
        }
        
        
    }
    
    @Override
    public boolean canMoveTo(Room targetRoom, Room playerRoom)
    {
        return !targetRoom.equals(playerRoom);
    }
    
    @Override
    public void examine(GameState gameState)
    {
        return;
    }
}




