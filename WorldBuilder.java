import java.util.HashMap;

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
        rooms.put("wreck", new Room("Wreck", "amid the wreckage of your crashed plane, frigid winds sweeping through the broken remains."));
        rooms.put("cabin", new Room("Cabin", "inside an old wooden cabin, cold but sheltered from the wind. A cold fireplace waits for firewood."));
        rooms.put("forest", new Room("Forest", "surrounded by dense, snow-laden trees, with quiet animal tracks leading deeper into the woods."));
        rooms.put("lake", new Room("Lake", "at the edge of a vast frozen lake, cracks spiderwebbing beneath your feet."));
        rooms.put("camp", new Room("Camp", "in a destroyed camp, torn tents and scattered belongings tell of a hasty departure."));
        rooms.put("cave", new Room("Cave", "in a glistening ice cave, the air cold and echoes bouncing off the walls. An old man sits nearby, wrapped in a heavy coat."));
        rooms.put("station", new Room("Station", "inside an abandoned research station, with tools and equipment left scattered across the tables."));
        rooms.put("storm", new Room("Storm", "in the midst of a blinding snowstorm, ice and snow whipping around you in every direction."));
        rooms.put("rescue", new Room("Rescue", "at a flat, open expanse, the remnants of an old fire pit the only signs of life in this desolate place."));
        
        
        // Set room exits
        rooms.get("wreck").setExit("east", rooms.get("cabin"));
        rooms.get("wreck").setExit("north", rooms.get("lake"));
        
        rooms.get("cabin").setExit("west", rooms.get("wreck"));
        rooms.get("cabin").setExit("east", rooms.get("forest"));
        rooms.get("cabin").setExit("north", rooms.get("lake"));
        
        rooms.get("forest").setExit("west", rooms.get("cabin"));
        
        rooms.get("lake").setExit("south", rooms.get("wreck"));
        rooms.get("lake").setExit("east", rooms.get("cabin"));
        rooms.get("lake").setExit("north", rooms.get("cave"));
        
        rooms.get("cave").setExit("south", rooms.get("lake"));
        rooms.get("cave").setExit("west", rooms.get("camp"));
        rooms.get("cave").setExit("north", rooms.get("storm"));
        rooms.get("cave").setExit("east", rooms.get("station"));
        
        rooms.get("camp").setExit("east", rooms.get("cave"));
        rooms.get("camp").setExit("north", rooms.get("storm"));
        
        rooms.get("station").setExit("west", rooms.get("cave"));
        rooms.get("station").setExit("north", rooms.get("storm"));
        
        rooms.get("storm").setExit("south", rooms.get("cave"));
        rooms.get("storm").setExit("east", rooms.get("station"));
        rooms.get("storm").setExit("north", rooms.get("rescue"));
        rooms.get("storm").setExit("west", rooms.get("camp"));

        return rooms;
    }

    public void placeItems(HashMap<String, Room> rooms) {
        // Create and place items
        rooms.get("wreck").addItem(new Item("Radio", "An old radio transmitter. It looks like it could be repaired.", 3));
        rooms.get("cabin").addItem(new Item("Rope", "A sturdy rope, useful for climbing or crossing difficult terrain.", 2));
        rooms.get("forest").addItem(new Item("Firewood", "Some dry firewood. It could be used to start a fire.", 2));
        rooms.get("forest").addItem(new Item("Armour", "Thick leather armour, insulated to keep you warm.", 5));
        rooms.get("camp").addItem(new Item("Torch", "A burning torch that can scare off wild animals.", 2));
        rooms.get("camp").addItem(new Item("Tools", "A set of tools, possibly useful for fixing the broken radio.", 4));
    }
    
    public void placeEntities(HashMap<String, Room> rooms) {
        
        rooms.get("lake").addEntity(
            new Unlockable(
                "lake",
                rooms.get("cabin").getItem("rope"),
                "The frozen lake is too dangerous to cross without assistance. The rope is your only chance.",
                "You successfully crossed the frozen lake using the rope."
            )
        );
        
        rooms.get("cabin").addEntity(
            new Fireplace(
                "fireplace",
                rooms.get("forest").getItem("firewood")
            )
        );
        
        rooms.get("cave").addEntity(
            new Person(
                "old-man",
                new Item("Battery", "A spare battery, could be useful for powering devices.", 1),
                rooms.get("camp").getItem("torch"),
                "The old man looks at you and says, 'Bring me a torch first, and I might help you.'"
            )
        );

    }

}
