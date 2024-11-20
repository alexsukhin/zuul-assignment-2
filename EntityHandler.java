
/**
 * Write a description of class EntityHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EntityHandler
{

    public void handleInteraction(Unlockable entity, boolean success) {
        
        if (success) {
            
            if (entity.getName().equals("Lake")) {
                System.out.println("Successfully crossed lake");
            }
            
        } else {
            
            if (entity.getName().equals("Lake")) {
                System.out.println("You need a rope to cross the lake");
            }            
            
        }
        
    }
}
