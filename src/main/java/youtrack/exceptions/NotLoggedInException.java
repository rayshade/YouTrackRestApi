package youtrack.exceptions;
import youtrack.commands.base.Command;
/**
 * Created by Egor.Malyshev on 05.08.2015.
 */
public class NotLoggedInException extends Exception {
    public Command getCommand() {
        return command;
    }
    private final Command command;
    public NotLoggedInException(Command command) {
        super("You must be logged in to run " + command);
        this.command = command;
    }
}
