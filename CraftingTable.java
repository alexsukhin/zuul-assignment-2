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
    Item tools;
    
    public CraftingTable(String name, Item tools) {
        super(name);
        this.recipes = new HashMap<>();
        this.tools = tools;
    }
    
    
    @Override
    public void examine(GameState gameState) {
        System.out.println("It's a sturdy wooden table with crafting tools.");
        System.out.println("Recipes:");
        for (CraftingRecipe recipe : recipes.values()) {
            System.out.println(recipe.getRecipe());
        }
    }
    
    public void addRecipe(String recipeName, Item ingredient, Item result)
    {
        CraftingRecipe recipe = new CraftingRecipe(ingredient, result);
        recipes.put(recipeName, recipe);
    }

    public boolean craftItem(GameState gameState, String itemName) {
        
        Inventory inventory = gameState.getInventory();
        
        if (inventory.hasItem(tools)) {
            
            CraftingRecipe recipe = recipes.get(itemName.toLowerCase());
        
            if (!(recipe == null)) {
                
                Item ingredient = recipe.getIngredient();
                Item item = recipe.getResult();
                
                if (inventory.hasItem(ingredient)) {
                        System.out.println("You craft the " + itemName + "!");
                        inventory.addItem(item);
                    
                } else {
                    System.out.println("You do not have the ingredients necessary to craft this item!");
                }
            
            } else {
                System.out.println("This is not valid item to craft...");
            }
        
        } else {
            return false;
        }
        
        return true;
    }
}
