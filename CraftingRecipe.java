import java.util.List;
/**
 * Write a description of class CraftingRecipe here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CraftingRecipe
{
    private Item ingredient;
    private Item result;
    
    public CraftingRecipe(Item ingredient, Item result)
    {
        this.ingredient = ingredient;
        this.result = result;
    }
    
    public Item getIngredient()
    {
        return ingredient;
    }
    
    public Item getResult()
    {
        return result;
    }
    
    public String getRecipe()
    {
        return "[" + result.getName() + "] - Requires " + ingredient.getName();
    }
    
}
