/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *  
 * This class holds information about commands that are issued by the user.
 * A command consists of three strings: a command word, a second word and
 * a third word. Commands are checked for being valid command words. If the 
 * user entered an invalid command, then the command word is null. If the
 * command had only one word, then the second and third word is null.
 * 
 * @author Alexander Sukhin
 * @version 2024.11.26
 */

public class Command
{
    private String commandWord;
    private String secondWord;
    private String thirdWord;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     * @param thirdWord The third word of the command.
     */
    public Command(String firstWord, String secondWord, String thirdWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * Returns the second word of this command. Returns null if there was no
     * second word in the command.
     * 
     * @return The second word of this command.
     */
    public String getSecondWord()
    {
        return secondWord.strip();
    }
    
    /**
     * Returns the third word of this command. Returns null if there was no
     * third word in the command.
     * 
     * @return The third word of this command.
     */
    public String getThirdWord()
    {
        return thirdWord.strip();
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    
    /**
     * @return true if the command has a third word.
     */
    public boolean hasThirdWord()
    {
        return (thirdWord != null);
    }
}

