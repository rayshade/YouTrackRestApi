package youtrack.exceptions;

import youtrack.CommandBasedList;

/**
 * Created by Egor.Malyshev on 11/30/2016.
 */
public class CommandNotAvailableException extends Exception {
    public CommandNotAvailableException(CommandBasedList owner, String commandName) {
        super(owner.toString() + " does not support " + commandName);

    }
}
