package youtrack.exceptions;

import youtrack.commands.Command;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class CommandNotAvailableException extends Exception {

    private final Command command;

    public CommandNotAvailableException(Command command) {
        super("Command not available: " + command.toString());
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
