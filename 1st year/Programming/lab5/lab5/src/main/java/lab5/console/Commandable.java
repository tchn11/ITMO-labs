package lab5.console;

public interface Commandable {
    /**
     * Execute command
     * @param arg Argument
     * @return End or not to end
     */
    boolean execute(String arg);
    /**
     * Get name of the command
     * @return Name
     */
    String getName();
    /**
     * Get description for 'help' command
     * @return Description
     */
    String getDescription();
}
