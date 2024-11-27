/**
 * This class represents a person in the game who can interact with the player.
 * A person may hold an item and require a specific item from the player in exchange 
 * for giving the player their held item. The class manages the interaction logic, 
 * ensuring the player can only receive the item once the required item is provided.
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */
public class Trader extends Entity {

    private String heldItem;        // item that the trader holds and may give to the player
    private String requiredItem;    // item that the player holds and may give to the trader for the held item
    private boolean hasRequiredItem; // indicates whether the person has already given the item to the player
    private String noItemMessage;   // message shown when the player tries to interact with the trader but doesn't have the required item

    /**
     * Initialises a new Trader instance.
     * 
     * @param name The name of the trader.
     * @param heldItem The name of the item the trader holds 
     * @param requiredItem The item the player needs to provide in exchange for the held item.
     * @param noItemMessage The message displayed when the player tries to interact but doesn't have the required item.
     */
    public Trader(String name, String heldItem, String requiredItem, String noItemMessage) {
        super(name);
        this.heldItem = heldItem;
        this.requiredItem = requiredItem;
        this.noItemMessage = noItemMessage;
        this.hasRequiredItem = false; // the trader initially doesn't have the required item
    }

    // Public Methods

    /**
     * Examines the trader. If the trader has already received the
     * required item, the trader has no more items to give. Otherwise,
     * displays the message indicating that the player still has to
     * give the required item to the trader.
     */
    @Override
    public void examine() {
        if (hasRequiredItem) {
            System.out.println(getName() + " has nothing more to give.");
        } else {
            System.out.println(noItemMessage); // Shows the no item message if the player hasn't provided the required item
        }
    }

    /**
     * Interacts with the trader. The player must give the required item to the trader
     * for the held item. If the trade is successful, the held item is added to the player's
     * inventory and the required item is removed from the player's inventory.
     * 
     * @param itemHandler The ItemHandler interface managing the player's inventory.
     * @param itemName The name of the item the player offers to the trader.
     */
    public void interact(ItemHandler itemHandler, String itemName) {
        if (hasRequiredItem) {
            System.out.println(getName() + " has already given you something.");
        } else if (itemName.equals(requiredItem)) {
            itemHandler.addItem(heldItem);  // Adds the held item to the player's inventory
            itemHandler.removeItem(itemName); // Removes the required item from the player's inventory
            hasRequiredItem = true;         // Marks that the player has received the item
            System.out.println(getName() + " gives you a " + heldItem + ".");
        } else {
            System.out.println(getName() + " doesn't want that item."); // If the wrong item is offered, this message is shown
        }
    }
}
