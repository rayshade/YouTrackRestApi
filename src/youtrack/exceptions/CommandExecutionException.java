package youtrack.exceptions;
import youtrack.Error;
import youtrack.commands.base.Command;
/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class CommandExecutionException extends Exception {
    private final Command command;
    private final youtrack.Error e;
    private final Exception ex;
    public CommandExecutionException(Command command, Error e) {
        super("Command failed: " + command.toString() + " " + e.getMessage());
        this.command = command;
        this.e = e;
        ex = null;
    }
    public CommandExecutionException(Command command, Exception e) {
        super("Command failed: " + command.toString() + " " + e.getMessage());
        this.command = command;
        this.ex = e;
        this.e = null;
    }
    public Exception getInnerException() {
        return ex;
    }
    public Error getError() {
        return e;
    }
    public Command getCommand() {
        return command;
    }
}