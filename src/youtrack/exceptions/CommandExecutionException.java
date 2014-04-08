package youtrack.exceptions;

import youtrack.commands.Command;

/**
 * Created by egor.malyshev on 08.04.2014.
 */
public class CommandExecutionException extends Exception {

	private final Command command;
	private final Exception e;

	public CommandExecutionException(Command command, Exception e) {
		super("Command not available: " + command.toString(), e);
		this.command = command;
		this.e = e;
	}

	public Command getCommand() {
		return command;
	}

}
