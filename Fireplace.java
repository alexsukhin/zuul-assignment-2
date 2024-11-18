
/**
 * Write a description of class Fireplace here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Fireplace extends Entity
{
    private boolean hasFire;
    
    public Fireplace(String name, String description) {
        super(name, description);
        this.hasFire = false;
    }
    
    @Override
    public void interact()
    {
        if (hasFire) {
            System.out.println("There is warmth! +5 to heat.");
        } else {
            System.out.println("There is no fire :(");
        }
    }
    
    public void addFire()
    {
        hasFire = true;
    }
}

