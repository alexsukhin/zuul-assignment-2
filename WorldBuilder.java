import java.util.Map;
import java.util.HashMap;

/**
 * Write a description of class WorldBuilder here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WorldBuilder
{
    public Map<String, Room> createRooms() {
        Map<String, Room> rooms = new HashMap<>();

        // Create rooms
        rooms.put("wreck", new Room("amid the remains of your crashed plane, frigid winds sweeping through"));
        rooms.put("cabin", new Room("inside an old wooden cabin, cold but sheltered from the wind"));
        rooms.put("forest", new Room("surrounded by dense, snow-laden trees, with quiet animal tracks nearby"));
        rooms.put("lake", new Room("at the edge of a vast frozen lake, cracks spiderwebbing beneath your feet"));
        rooms.put("camp", new Room("in a destroyed camp, ravaged by wolves, with torn tents and scattered belongings"));
        rooms.put("cave", new Room("in a glistening ice cave, the air chilling and echoes bouncing around"));
        rooms.put("station", new Room("inside an abandoned research station, tools scattered across tables"));
        rooms.put("storm", new Room("in the midst of a blinding snowstorm, with ice and snow whipping around you"));
        rooms.put("rescue", new Room("at a flat, open expanse, perfect for signaling with the remnants of an old fire nearby"));

        // Set room exits
        rooms.get("wreck").setExit("east", rooms.get("cabin"));
        rooms.get("wreck").setExit("north", rooms.get("lake"));
        
        rooms.get("cabin").setExit("west", rooms.get("wreck"));
        rooms.get("cabin").setExit("east", rooms.get("forest"));
        rooms.get("cabin").setExit("north", rooms.get("lake"));
        
        rooms.get("forest").setExit("west", rooms.get("cabin"));
        
        rooms.get("lake").setExit("south", rooms.get("wreck"));
        rooms.get("lake").setExit("east", rooms.get("cabin"));
        rooms.get("lake").setExit("north", rooms.get("camp"));
        
        rooms.get("camp").setExit("north", rooms.get("cave"));
        rooms.get("camp").setExit("south", rooms.get("lake"));
        
        rooms.get("cave").setExit("south", rooms.get("camp"));
        rooms.get("cave").setExit("north", rooms.get("storm"));
        rooms.get("cave").setExit("east", rooms.get("station"));
        
        rooms.get("station").setExit("west", rooms.get("cave"));
        rooms.get("station").setExit("north", rooms.get("storm"));
        
        rooms.get("storm").setExit("south", rooms.get("cave"));
        rooms.get("storm").setExit("east", rooms.get("station"));
        rooms.get("storm").setExit("north", rooms.get("rescue"));

        return rooms;
    }

    public void placeItems(Map<String, Room> rooms) {
        // Create and place items
        rooms.get("wreck").addItem(new Item("Radio", "An old radio transmitter. It looks like it could be repaired.", 3));
        rooms.get("cabin").addItem(new Item("Rope", "A sturdy rope, useful for climbing or crossing difficult terrain.", 2));
        rooms.get("forest").addItem(new Item("Firewood", "Some dry firewood. It could be used to start a fire.", 2));
        rooms.get("forest").addItem(new Item("Armour", "Thick leather armour, insulated to keep you warm.", 5));
        rooms.get("camp").addItem(new Item("Torch", "A burning torch that can scare off wild animals.", 2));
        rooms.get("camp").addItem(new Item("Tools", "A set of tools, possibly useful for fixing the broken radio.", 4));
        rooms.get("station").addItem(new Item("Battery", "A spare battery, could be useful for powering devices.", 1));
    }
    
    public void placeEntities(Map<String, Room> rooms, Map<String, Item> items) {
        rooms.get("lake").addEntity(new Unlockable("lake"), "", );
    }
}
