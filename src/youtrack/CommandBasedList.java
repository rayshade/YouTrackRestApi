package youtrack;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.CommandNotAvailableException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by egor.malyshev on 03.04.2014.
 * <p/>
 * Operates with items of R owned by items of O.
 * For example, CommandBasedList<YouTrack, Issue> allows handling issue from project.
 */
@SuppressWarnings({"SameParameterValue", "WeakerAccess", "unused"})
public class CommandBasedList<O extends BaseItem, R extends BaseItem<O>> {
    private final O owner;
    private final AddCommand<O, R> addCommand;
    private final RemoveCommand<O, R> removeCommand;
    private final ListCommand<O, R> listCommand;
    private final ListCommand<O, R> queryCommand;
    private final SingleItemCommand<O, R> singleItemCommand;

    CommandBasedList(@NotNull O owner,
                     @Nullable AddCommand<O, R> addCommand,
                     @Nullable RemoveCommand<O, R> removeCommand,
                     @Nullable ListCommand<O, R> listCommand,
                     @Nullable ListCommand<O, R> queryCommand,
                     @Nullable SingleItemCommand<O, R> singleItemCommand) {
        this.owner = owner;
        this.addCommand = addCommand;
        this.removeCommand = removeCommand;
        this.listCommand = listCommand;
        this.queryCommand = queryCommand;
        this.singleItemCommand = singleItemCommand;
    }

    @NotNull
    public void add(final @NotNull R item) throws CommandExecutionException, IOException, CommandNotAvailableException {
        if (addCommand == null) throw new CommandNotAvailableException(this, "addCommand");
        addCommand.setItem(item);
        owner.getYouTrack().execute(addCommand);
    }

    @NotNull
    public void remove(final @NotNull R item) throws CommandExecutionException, IOException, CommandNotAvailableException {
        if (removeCommand == null) throw new CommandNotAvailableException(this, "removeCommand");
        removeCommand.setItem(item);
        owner.getYouTrack().execute(removeCommand);
    }

    @Nullable
    public R item(final int index) throws CommandExecutionException, IOException, CommandNotAvailableException {
        return this.list().get(index);
    }

    @Override
    public String toString() {
        return "CommandBasedList{" +
                "owner=" + owner + '}';
    }

    @Nullable
    public R item(final @NotNull String id) throws CommandExecutionException, IOException, CommandNotAvailableException {
        if (singleItemCommand == null) throw new CommandNotAvailableException(this, "singleItemCommand");
        singleItemCommand.setItemId(id);
        return owner.getYouTrack().execute(singleItemCommand).getResult();
    }

    @NotNull
    public List<R> list() throws CommandExecutionException, IOException, CommandNotAvailableException {
        if (listCommand == null) throw new CommandNotAvailableException(this, "listCommand");
        final CommandResult<List<R>> result = owner.getYouTrack().execute(listCommand);
        return result.success() ? result.getResult() : Collections.<R>emptyList();
    }

    @NotNull
    public List<R> query(final @NotNull String query, final int start, final int maxResults) throws CommandExecutionException, IOException, CommandNotAvailableException {
        if (queryCommand == null) throw new CommandNotAvailableException(this, "queryCommand");
        queryCommand.setParameter("filter", query);
        queryCommand.setParameter("max", String.valueOf(maxResults));
        queryCommand.setParameter("after", String.valueOf(start));
        final CommandResult<List<R>> result = owner.getYouTrack().execute(queryCommand);
        return result.success() ? result.getResult() : Collections.<R>emptyList();
    }

    public List<R> query(final @NotNull String query) throws CommandExecutionException, IOException, CommandNotAvailableException {
        return query(query, 0, 100);
    }
}