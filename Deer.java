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
    
    public Deer(List<Room> rooms, Item knife)
    {
        super("deer", rooms);
        this.knife = knife;
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

    @Override
    public void interact(GameState gameState, Item item)
    {
        if (item.equals(knife)) {
            //kill deer and gain leather
        } else {
            System.out.println("That item doesn't work here.");
        }
    }
}
