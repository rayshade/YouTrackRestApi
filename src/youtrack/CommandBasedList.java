package youtrack;

import com.sun.istack.internal.NotNull;
import youtrack.commands.Command;
import youtrack.commands.results.Result;
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

	CommandBasedList(Item owner, Class addCommand, Class removeCommand, Class listCommand) {
		this.owner = owner;
		this.addCommand = addCommand;
		this.removeCommand = removeCommand;
		this.listCommand = listCommand;
	}

	public T add(T item) throws IOException, NoSuchIssueFieldException, CommandNotAvailableException, CommandExecutionException {
		Command command;
		try {
			command = (Command) addCommand.getDeclaredConstructors()[0].newInstance(owner, item);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandNotAvailableException(addCommand);
		}

		Result result = owner.getYouTrack().execute(command);

		if (result.success()) {

			List<T> itemList = this.list();

			return itemList.get(itemList.size() - 1);

		} else return null;
	}

	public void remove(T item) throws IOException, NoSuchIssueFieldException, CommandNotAvailableException {
		try {
			Command command = (Command) removeCommand.getDeclaredConstructors()[0].newInstance(owner, item);
			owner.getYouTrack().execute(command);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandNotAvailableException(removeCommand);
		}
	}

	public T item(int index) throws CommandNotAvailableException, CommandExecutionException, NoSuchIssueFieldException, IOException {
		return this.list().get(index);
	}

	public T item(@NotNull String id) throws CommandNotAvailableException, CommandExecutionException, NoSuchIssueFieldException, IOException {
		for (T t : this.list()) {
			if (t.getId().equals(id)) return t;
		}
		return null;
	}

	public List<T> list() throws CommandNotAvailableException, IOException, NoSuchIssueFieldException, CommandExecutionException {
		Result result;
		Command command;
		try {

			command = (Command) listCommand.getDeclaredConstructors()[0].newInstance(owner);

		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandNotAvailableException(listCommand);
		}

		result = owner.getYouTrack().execute(command);

		if (result.success()) {

			List<T> data = (List<T>) result.getData();
			for (T t : data) {
				t.setYouTrack(owner.youTrack);
			}
			return data;

		} else return null;

	}

}