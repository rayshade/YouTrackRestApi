package youtrack;

import com.sun.istack.internal.NotNull;
import youtrack.commands.Command;
import youtrack.commands.CommandResult;
import youtrack.commands.QueryParameters;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.CommandNotAvailableException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;
import java.util.List;

/**
 * Created by egor.malyshev on 03.04.2014.
 * <p/>
 * Simplifies working with comments, attachments and links in issues.
 */

@SuppressWarnings("unchecked")
public class CommandBasedList<T extends Item> {

	private final Item owner;
	private final Class addCommand;
	private final Class removeCommand;
	private final Class listCommand;
	private final Class queryCommand;
	private final Class getSingleItemCommand;

	CommandBasedList(Item owner, Class addCommand, Class removeCommand, Class listCommand, Class queryCommand, Class getSingleItemCommand) {
		this.owner = owner;
		this.addCommand = addCommand;
		this.removeCommand = removeCommand;
		this.listCommand = listCommand;
		this.queryCommand = queryCommand;
		this.getSingleItemCommand = getSingleItemCommand;
	}

	public T add(T item) throws IOException, NoSuchIssueFieldException, CommandNotAvailableException, CommandExecutionException {
		Command command;
		try {
			command = (Command) addCommand.getDeclaredConstructors()[0].newInstance(owner, item);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandNotAvailableException(addCommand);
		}

		CommandResult result = owner.getYouTrack().execute(command);

		if (result.success()) {

			List<T> itemList = this.list();

			return itemList.get(itemList.size() - 1);

		} else return null;
	}

	public void remove(T item) throws IOException, NoSuchIssueFieldException, CommandNotAvailableException, CommandExecutionException {
		Command command;
		try {
			command = (Command) removeCommand.getDeclaredConstructors()[0].newInstance(owner, item);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandNotAvailableException(removeCommand);
		}
		owner.getYouTrack().execute(command);
	}

	public T item(int index) throws CommandNotAvailableException, CommandExecutionException, NoSuchIssueFieldException, IOException {
		return this.list().get(index);
	}

	public T item(@NotNull String id) throws CommandNotAvailableException, CommandExecutionException, NoSuchIssueFieldException, IOException {
		CommandResult result;
		Command command;
		try {

			command = (Command) getSingleItemCommand.getDeclaredConstructors()[0].newInstance(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandNotAvailableException(getSingleItemCommand);
		}

		result = owner.getYouTrack().execute(command);

		if (result.success()) {

			return (T) result.getData();

		} else return null;

	}

	public List<T> list() throws CommandNotAvailableException, IOException, NoSuchIssueFieldException, CommandExecutionException {
		CommandResult result;
		Command command;
		try {

			command = (Command) listCommand.getDeclaredConstructors()[0].newInstance(owner);

		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandNotAvailableException(listCommand);
		}

		result = owner.getYouTrack().execute(command);

		if (result.success()) {

			return getInitializedList(result);

		} else return null;

	}

	protected List<T> getInitializedList(CommandResult result) {
		List<T> data = (List<T>) result.getData();

		for (T t : data) {
			t.setYouTrack(owner.youTrack);
		}

		return data;
	}

	public List<T> query(String query) throws CommandNotAvailableException, NoSuchIssueFieldException, IOException, CommandExecutionException {
		CommandResult result;
		Command command;

		try {
			command = (Command) queryCommand.getDeclaredConstructors()[0].newInstance(owner, new QueryParameters(query, 100, 0));
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandNotAvailableException(queryCommand);
		}

		result = owner.getYouTrack().execute(command);

		if (result.success()) {
			return getInitializedList(result);
		} else return null;
	}
}