package youtrack.exceptions;

import youtrack.commands.Command;

/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class CommandExecutionException extends Exception {

    private final Command command;

    public CommandExecutionException(Command command, Exception e) {
        super("Command failed: " + command.toString() + " " + e.getMessage(), e);
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

}
