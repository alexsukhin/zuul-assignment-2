
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
    
    public Person(String name, Item heldItem, Item requiredItem, String noItemMessage) {
        super(name);
        this.heldItem = heldItem;
        this.requiredItem = requiredItem;
        this.noItemMessage = noItemMessage;
        this.hasRequiredItem = false;
    }
    
    @Override
    public void examine(GameState gameState) {
        if (hasRequiredItem) {
            System.out.println(getName() + " has nothing more to give.");
        } else {
            System.out.println(noItemMessage);
        }
    }

    public void interact(GameState gameState, Item item) {
        if (hasRequiredItem) {
            System.out.println(getName() + " has already given you something.");
        } else if (item.equals(requiredItem)) {
            gameState.getInventory().addItem(heldItem);
            gameState.getInventory().removeItem(item);
            hasRequiredItem = true;
            System.out.println(getName() + " gives you a " + heldItem.getName() + ".");
        } else {
            System.out.println(getName() + " doesn't want that item.");
        }
    }
    
    public Item getRequiredItem()
    {
        return requiredItem;
    }
}