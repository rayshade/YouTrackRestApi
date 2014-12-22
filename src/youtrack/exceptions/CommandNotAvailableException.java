package youtrack.exceptions;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class CommandNotAvailableException extends Exception {

    private final Class command;

    public CommandNotAvailableException(Class command, Exception e) {
        super("Command not available: " + command.toString() + ": " + e.getMessage());
        this.command = command;
    }

    public Class getCommand() {
        return command;
    }
}
