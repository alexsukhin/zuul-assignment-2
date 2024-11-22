import java.util.List;
import java.util.HashMap;

/**
 * Write a description of class CraftingTable here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CraftingTable extends Entity
{
    HashMap<String, CraftingRecipe> recipes;
    
    public CraftingTable(String name) {
        super(name);
        this.   recipes = new HashMap<>();
    }
    
    
    @Override
    public void examine(GameState gameState) {
        System.out.println("It's a sturdy wooden table with crafting tools.");
    }

    @Override
    public void interact(GameState gameState, Item item) {
        if (item == null) {
            System.out.println("You need materials to craft something.");
        } else {
            craftItem(gameState, item);
        }
    }
    
    public void addRecipe(String recipeName, List<Item> ingredients, Item result)
    {
        CraftingRecipe recipe = new CraftingRecipe(ingredients, result);
        recipes.put(recipeName, recipe);
    }

    private void craftItem(GameState gameState, Item item) {
        System.out.println("You craft a " + item.getName() + "!");
        gameState.getInventory().addItem(item);
    }
}
