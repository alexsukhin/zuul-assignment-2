import java.util.List;

/**
 * Write a description of class SnowWolf here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SnowWolf extends MovingEntity
{
    public SnowWolf(List<Room> rooms)
    {
        super("Snow Wolf", rooms);
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
