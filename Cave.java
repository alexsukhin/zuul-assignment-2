
/**
 * Write a description of class Cave here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Cave extends Unlockable {
    public Cave(String name) {
        super(name, null, 
              "The cave provides shelter from the cold.", 
              "The cave is quiet and provides a safe place to rest.",
              null,
              false);
    }

    @Override
    public void rest(GameState gameState) {
        System.out.println("You rest in the cave and feel fully rejuvenated.");
        gameState.adjustHeat(gameState.getMaxHeat());
    }
}