
/**
 * Write a description of class Person here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Person extends Entity 
{
    private Item heldItem;
    private Item requiredItem;
    private boolean hasRequiredItem;
    private String noItemMessage;
    
    public Person(String name, String description, Item heldItem, Item requiredItem, String noItemMessage) {
        super(name, description);
        this.heldItem = heldItem;
        this.requiredItem = requiredItem;
        this.noItemMessage = noItemMessage;
        this.hasRequiredItem = false;
    }
    
    @Override
    public void interact(GameState gameState) {
        
        Inventory inventory = gameState.getInventory();
        
        if (hasRequiredItem) {
            System.out.println(getName() + "has nothing more to give.");
        } else if (inventory.hasItem(requiredItem)) {
            inventory.addItem(heldItem);
            System.out.println(getName() + "gives you a " + heldItem.getName() + ": " + heldItem.getDescription());
            hasRequiredItem = true;
            
            inventory.removeItem(requiredItem);
            System.out.println(getName() + "takes the " + requiredItem.getName() + " in exchange");
        } else {
            System.out.println(noItemMessage);
        }

    }
}