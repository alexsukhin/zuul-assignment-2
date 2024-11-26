import java.util.HashMap;
import java.util.ArrayList;

/**
 * Write a description of class WorldBuilder here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WorldBuilder
{
    public HashMap<String, Room> createRooms() {
        HashMap<String, Room> rooms = new HashMap<>();

        // Create rooms with concise, immersive descriptions and names as separate parameters
        rooms.put("wreck", new Room("Wreck", "amid the wreckage of your crashed plane, frigid winds sweeping through the broken remains.", 10));
        rooms.put("cabin", new Room("Cabin", "inside an old wooden cabin, cold but sheltered from the wind. A cold fireplace waits for firewood.", 5));
        rooms.put("forest", new Room("Forest", "surrounded by dense, snow-laden trees, with quiet animal tracks leading deeper into the woods.", 10));
        rooms.put("lake", new Room("Lake", "at the edge of a vast frozen lake, cracks spiderwebbing beneath your feet.", 20));
        rooms.put("camp", new Room("Camp", "in a destroyed camp, torn tents and scattered belongings tell of a hasty departure.", 10));
        rooms.put("cave", new Room("Cave", "in a glistening ice cave, the air cold and echoes bouncing off the walls. An old man sits nearby, wrapped in a heavy coat.", 10));
        rooms.put("station", new Room("Station", "inside an abandoned research station, with tools and equipment left scattered across the tables.", 5));
        rooms.put("storm1", new Room("Edge of the Storm", "in the midst of a blinding snowstorm, ice and snow whipping around you in every direction.\nThe freezing wind drains your warmth.", 50));
        rooms.put("storm2", new Room("Storm's Fury", "deep in the storm, where ice and snow slice through the air like razors.\nThe cold grows unbearable, stealing your heat.", true, 50));
        rooms.put("storm3", new Room("Heart of the Blizzard", "in the heart of the storm, all is chaos—wind howls, and frost coats everything in sight.\nThe chill is relentless, sapping the last of your strength.", 45));
        
        rooms.put("rescue", new Room("Rescue", "at a flat, open expanse, the remnants of an old fire pit the only signs of life in this desolate place.", 0));
        
        
        // Set room exits
        rooms.get("wreck").setExit("east", rooms.get("cabin"));
        rooms.get("wreck").setExit("north", rooms.get("lake"));
        
        rooms.get("cabin").setExit("west", rooms.get("wreck"));
        rooms.get("cabin").setExit("east", rooms.get("forest"));
        
        rooms.get("forest").setExit("west", rooms.get("cabin"));
        
        rooms.get("lake").setExit("south", rooms.get("wreck"));
        rooms.get("lake").setExit("north", rooms.get("cave"));
        
        rooms.get("cave").setExit("south", rooms.get("lake"));
        rooms.get("cave").setExit("west", rooms.get("camp"));
        rooms.get("cave").setExit("east", rooms.get("station"));
        rooms.get("cave").setExit("north", rooms.get("storm1"));
        
        rooms.get("camp").setExit("east", rooms.get("cave"));
        
        rooms.get("station").setExit("west", rooms.get("cave"));
        
        rooms.get("storm1").setExit("north", rooms.get("storm2"));
        rooms.get("storm1").setExit("south", rooms.get("cave"));
        
        rooms.get("storm2").setExit("north", rooms.get("storm3"));
        rooms.get("storm2").setExit("south", rooms.get("storm1"));
        
        rooms.get("storm3").setExit("north", rooms.get("rescue"));
        return rooms;
    }

    public HashMap<String, Item> createItems(HashMap<String, Room> rooms) {
        HashMap<String, Item> items = new HashMap<>();
        
        items.put("radio", new Item("Radio", "An old radio transmitter. It looks like it could be repaired.", 3, true));
        items.put("rope", new Item("Rope", "A sturdy rope, useful for climbing or crossing difficult terrain.", 2, true));
        items.put("firewood", new Item("Firewood", "Some dry firewood. It could be used to start a fire.", 2, true));
        items.put("torch", new Item("Torch", "A burning torch that can scare off wild animals.", 2, true));
        items.put("tools", new Item("Tools", "A set of tools, possibly useful for fixing the broken radio.", 4, true));
        items.put("knife", new Item("Knife", "A knife, useful for killing animals.", 2, true));
        items.put("battery", new Item("Battery", "A spare battery, could be useful for powering devices.", 1, true));
        items.put("repaired-radio", new Item("Repaired-Radio", "A repaired radio transmitter. It looks like it could be used for communication.", 3, true));
        items.put("leather", new Item("Leather", "Spare leather, could be useful for crafting armour.", 1, true));
        items.put("armour", new Item("Armour", "Thick leather armour, insulated to keep you warm.", 5, true));
        
        items.put("stove", new Item("Stove", "A heavy iron stove, cold and silent, standing in the corner of the room.", 0, false));
        items.put("tarp", new Item("Broken-Tent ", "A broken tent frame, collapsed under the weight of snow and time.", 0, false));
        items.put("microscope", new Item("Microscope", "A broken microscope, its lens cracked but still sitting on a workbench.", 0, false));
        
        // Create and place items
        rooms.get("wreck").addItem(items.get("radio"));
        rooms.get("cabin").addItem(items.get("rope"));
        rooms.get("forest").addItem(items.get("firewood"));
        rooms.get("camp").addItem(items.get("torch"));
        rooms.get("camp").addItem(items.get("tools"));
        rooms.get("lake").addItem(items.get("knife"));
        
        rooms.get("cabin").addItem(items.get("stove"));
        rooms.get("camp").addItem(items.get("tarp"));
        rooms.get("station").addItem(items.get("microscope"));
        
        return items;
    }
    
    public HashMap<String, Entity> createEntities(HashMap<String, Room> rooms, HashMap<String, Item> items) {
        
        HashMap<String, Entity> entities = new HashMap<>();
        
        entities.put("lake", 
            new Unlockable(
                "lake",
                items.get("rope"),
                "The frozen lake is too dangerous to cross without assistance. The rope is your only chance.",
                null,
                "You successfully crossed the frozen lake using the rope.",
                false
            ));
            
        entities.put("fireplace",
            new RestingPlace(
                "fireplace",
                items.get("firewood"),
                "The fireplace is cold and needs firewood to start a fire.",
                "The fire is burning brightly, keeping you warm.",
                "You put firewood in the fireplace and light it. Warmth spreads around!",
                false
            )
        );
        
        entities.put("cave-rest",
            new RestingPlace(
                "cave-rest",
                null,
                "The cave provides shelter from the cold.",
                "The cave is quiet and provides a safe place to rest.",
                null,
                true
            )
        );
        
        entities.put("old-man",
            new Person(
                "old-man",
                items.get("battery"),
                items.get("torch"),
                "The old man looks at you and says, 'Bring me a torch first, and I might help you.'"
            )
        );
        
        entities.put("snow-wolf",
            new SnowWolf(
                new ArrayList<Room>() {
                    {
                        add(rooms.get("camp"));
                        add(rooms.get("storm"));
                        add(rooms.get("station"));
                    }
                },
                items.get("knife")
            )
        );
        
        entities.put("deer",
            new Deer(
                new ArrayList<Room>() {
                    {
                        add(rooms.get("forest"));
                        add(rooms.get("cabin"));
                        add(rooms.get("wreck"));
                        add(rooms.get("lake"));
                    }
                },
                items.get("knife")
            )
        );
        
        CraftingTable craftingTable = new CraftingTable(
            "crafting-table",
            items.get("tools")
        );
        

        craftingTable.addRecipe(
            "repaired-radio",
            items.get("battery"),
            items.get("repaired-radio")
        );
    
        craftingTable.addRecipe(
            "armour",
            items.get("leather"),
            items.get("armour")
        );
        
        entities.put("crafting-table", craftingTable);
        
        
        
        rooms.get("lake").addEntity(entities.get("lake"));
        rooms.get("cabin").addEntity(entities.get("fireplace"));
        rooms.get("cave").addEntity(entities.get("cave-rest"));
        rooms.get("cave").addEntity(entities.get("old-man"));
        rooms.get("camp").addEntity(entities.get("snow-wolf"));
        rooms.get("forest").addEntity(entities.get("deer"));
        rooms.get("station").addEntity(entities.get("crafting-table"));
        
        return entities;
    }

}
