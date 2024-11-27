import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

/**
 * This class is a factory class responsible for creating entities based on a specified type.
 * This centralizes the creation logic for entities. 
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class EntityFactory
{

    /**
     * Generates new entities based on the provided type given to the function to
     * be assigned to rooms within the WorldBuilder Class. A switch statement is
     * used to create these entities for efficiency.
     * @param type The type of the entity to create (e.g., "lake", "fireplace", "snow-wolf").
     * @param rooms A map of room names to room objects, used for assigning rooms to certain entities.
     * @param items A map of item names to item objects, used for checking required items for certain entities.
     * @return A new entity instance of the specified type.
     * @throws IllegalArgumentException if the entity type is not recognized.
     */
    public static Entity createEntity(
        String type,
        HashMap<String, Room> rooms,
        HashMap<String, Item> items
    ) {
        switch (type.toLowerCase()) {
            
            // Creates an Unlockable entity
            case "lake":
                return new Unlockable(
                    "lake",
                    "rope",
                    "The frozen lake is too dangerous to cross without assistance. The rope is your only chance.",
                    null, // unlocked message - it is null here as it is not needed
                    "You successfully crossed the frozen lake using the rope.",
                    false // indicates doesn't consume item on use
                );

            // Creates a RestingPlace entity
            case "fireplace":
                return new RestingPlace(
                    "fireplace",
                    "firewood",
                    "The fireplace is cold and needs firewood to start a fire.",
                    "The fire is burning brightly, keeping you warm.",
                    "You put firewood in the fireplace and light it. Warmth spreads around!",
                    false
                );

            // Creates a RestingPlace entity
            case "cave-rest":
                return new RestingPlace(
                    "cave-rest",
                    null, // no required item
                    "The cave provides shelter from the cold.",
                    "The cave is quiet and provides a safe place to rest.",
                    null, // no use item message
                    true
                );

            // Creates a Trader entity
            case "old-man":
                return new Trader(
                    "old-man",
                    "battery",
                    "torch",
                    "The old man looks at you and says, 'Bring me a torch first, and I might help you.'"
                );

            // Creates a SnowWolf entity
            case "snow-wolf":
                return new SnowWolf(
                    new ArrayList<String>() { // rooms that the SnowWolf may move to
                        { 
                            add("camp");
                            add("storm1");
                            add("station");
                        }
                    },
                    "knife"
                );

            // Creates a Deer entity
            case "deer":
                return new Deer(
                    new ArrayList<String>() { // rooms that the Deer may move to
                        { 
                            add("forest");
                            add("cabin");
                            add("wreck");
                            add("lake");
                        }
                    },
                    "knife"
                );

            // Creates a CraftingTable entity
            case "crafting-table":
                CraftingTable craftingTable = new CraftingTable(
                    "crafting-table",
                    "tools"
                );

                // Recipes for crafting items
                craftingTable.addRecipe("repaired-radio", Arrays.asList("radio", "battery"));

                craftingTable.addRecipe("armour", Arrays.asList("leather"));

                return craftingTable;

            // Default case for unknown entity types
            default:
                throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }
}