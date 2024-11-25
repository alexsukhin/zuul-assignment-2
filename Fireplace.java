
/**
 * Write a description of class Fireplace here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Fireplace extends Unlockable
{
    private int numRests;
    
    public Fireplace(String name, Item requiredItem) {
        super(name, requiredItem, 
              "The fireplace is cold and needs firewood to start a fire.", 
              "The fire is burning brightly, keeping you warm.",
              "You put firewood in the fireplace and light it. Warmth spreads around!",
              true);
        numRests = 2;
    }
    
   
    @Override
    public void rest(GameState gameState)
    {  
        
        if (isUnlocked()) {
            if (numRests > 0) {
                System.out.println("You rest by the fireplace and regain some warmth");
                numRests -=1;
                gameState.adjustHeat(20);
            } else {
                System.out.println("The fireplace breaks. You cannot rest anymore!");
                lock();
            }
        } else {
            System.out.println(getLockedMessage());
        }
    }
}







