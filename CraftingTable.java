import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a crafting table in the game where players can craft
 * new items using ingredients. It allows for managing recipes, displaying
 * crafting options and executing the crafting process if the player has the 
 * necessary tools and ingredient.
 * 
 * This class uses a hashmap to store recipes, where each recipe maps
 * a resulting item to its required ingredient.
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class CraftingTable extends Entity
{
    
    private HashMap<String, List<String>> recipes;  // a map of item names to their required ingredients
    private String tools;                     // name of item tools required to craft items
    
    /**
     * Initialises a new CraftingTable instance with a name and the required tool.
     * 
     * @param name The name of the crafting table.
     * @param tools The name of the item tools required to craft items.
     */
    public CraftingTable(String name, String tools)
    {
        super(name); 
        this.recipes = new HashMap<>();
        this.tools = tools;
    }

    // Public methods
    
    /**
     * Examines the crafting table. Displays a description of the
     * table and its available recipes.
     */
    @Override
    public void examine()
    {
        System.out.println("It's a sturdy wooden table with crafting tools.");
        System.out.println("Recipes:");
        
        // Iterates through the recipes map and displays each recipe
        for (Map.Entry<String, List<String>> entry : recipes.entrySet()) {
            System.out.println("[" + entry.getKey() + "] - requires " + entry.getValue());
        }
    }

    /**
     * Adds a new recipe to the crafting table's hashmap of recipes.
     * 
     * @param result The item that will be created by crafting.
     * @param ingredient A list of ingredients required to craft the item.
     */
    public void addRecipe(String result, List<String> ingredient)
    {
        recipes.put(result, ingredient);
    }
    
    /**
     * Attempts to craft an item if the player has the required tool and ingredients.
     * If successful, the item is added to the player's inventory.
     * 
     * @param itemHandler The ItemHandler interface managing the player's inventory.
     * @param itemName The name of the item the player wants to craft.
     * @return true if the item is crafted successfully, false otherwise.
     */
    public boolean craftItem(ItemHandler itemHandler, String itemName)
    {
        // Retrieves the list of ingredients for the item from the recipes hashmap
        List<String> ingredients= recipes.get(itemName.toLowerCase());
    
        // Checks if the recipe exists
        if (ingredients == null) {
            System.out.println("This is not a valid item to craft...");
            return false;
        }
        
        // Checks if the player has the required tools
        if (!itemHandler.hasItem(tools)) {
            System.out.println("You do not have the necessary tools to craft this item.");
            return false;
        }
        
        // Checks if the player has all required ingredients
        for (String ingredient : ingredients) {
            if (!itemHandler.hasItem(ingredient)) {
                System.out.println("You are missing " + ingredient + " to craft this item!");
                return false;
            }
        }
        
        // Consumes ingredients and crafts the item
        for (String ingredient : ingredients) {
            itemHandler.removeItem(ingredient);
        }
        
        itemHandler.addItem(itemName);
        System.out.println("You craft the " + itemName + "!");    
        return true;
    }
}
