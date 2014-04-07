package youtrack;

import youtrack.commands.Command;
import youtrack.commands.results.Result;

import java.util.List;

/**
 * Created by egor.malyshev on 03.04.2014.
 *
 * Simplifies working with comments, attachments and links in issues.
 */

@SuppressWarnings("unchecked")
public class CommandBasedList<T> {

	private final Issue owner;
	private final Class addCommand;
	private final Class removeCommand;
	private final Class listCommand;

	public CommandBasedList(Issue owner, Class addCommand, Class removeCommand, Class listCommand) {
		this.owner = owner;
		this.addCommand = addCommand;
		this.removeCommand = removeCommand;
		this.listCommand = listCommand;
	}

	public T add(T item) {
		try {

			Command command = (Command) addCommand.getDeclaredConstructors()[0].newInstance(owner, item);

			Result result = owner.getYouTrack().execute(command);

			if (result.success()) {

				List<T> itemList = this.list();

				return itemList.get(itemList.size() - 1);

			} else return null;

		} catch (Exception e) {
			return null;
		}
	}

	public boolean remove(T item) {

		try {
			Command command = (Command) removeCommand.getDeclaredConstructors()[0].newInstance(owner, item);

			Result result = owner.getYouTrack().execute(command);

			return result.success();

		} catch (Exception e) {
			return false;
		}
	}

	public List<T> list() {

		try {
			Command command = (Command) listCommand.getDeclaredConstructors()[0].newInstance(owner);

			Result result = owner.getYouTrack().execute(command);

			if (result.success()) {

				return (List<T>) result.getData();

			} else return null;

		} catch (Exception e) {
			return null;
		}

	}
}