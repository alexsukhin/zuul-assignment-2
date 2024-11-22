import java.util.List;

/**
 * Write a description of class Deer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Deer extends MovingEntity
{
    public Deer(List<Room> rooms)
    {
        super("Deer", rooms);
    }
    
    @Override
    public void examine(GameState gameState)
    {
        return;
    }

    @Override
    public void interact(GameState gameState, Item item)
    {
        return;
    }
}
