import java.util.List;

/**
 * Write a description of class Deer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Deer extends MovingEntity
{
    private Item knife;
    private boolean alive;
    
    public Deer(List<Room> rooms, Item knife)
    {
        super("deer", rooms);
        this.knife = knife;
        this.alive = true;
    }
    
    @Override
    public boolean canMoveTo(Room targetRoom, Room playerRoom)
    {
        return true;
    }
    
    @Override
    public void examine(GameState gameState)
    {
        return;
    }

    public boolean killDeer(GameState gameState, Item item)
    {
        if (item.equals(knife)) {
            System.out.println("You kill the deer! You gain a piece of leather for your troubles.");
            alive = false;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isAlive()
    {
        if (alive) {
            return true;
        } else {
            return false;
        } 
    }
}
