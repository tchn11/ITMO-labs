package server.commands;

import messages.AnswerMsg;

public interface Commandable {
    /**
     * Execute command
     * @param arg Argument
     * @param obArg Object argument
     * @param ans Answer object
     * @return End or not to end
     */
    boolean execute(String arg, Object obArg, AnswerMsg ans);
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
