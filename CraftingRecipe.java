import java.util.List;
/**
 * Write a description of class CraftingRecipe here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CraftingRecipe
{
    private List<Item> ingredients;
    private Item result;
    
    public CraftingRecipe(List<Item> ingredients, Item result)
    {
        this.ingredients = ingredients;
        this.result = result;
    }
    
    public List<Item> getIngredients()
    {
        return ingredients;
    }
    
    public Item getResult()
    {
        return result;
    }
    
}
