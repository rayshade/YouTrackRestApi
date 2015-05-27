package youtrack;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import youtrack.commands.base.*;
import youtrack.exceptions.CommandExecutionException;

import java.util.Collections;
import java.util.List;
/**
 * Created by egor.malyshev on 03.04.2014.
 * <p/>
 * Operates with items of R owned by items of O.
 * For example, CommandBasedList<Project, Issue> allows handling issues from project.
 */
public class CommandBasedList<O extends BaseItem, R extends BaseItem> {
    private final O owner;
    private final AddCommand<O, R> addCommand;
    private final RemoveCommand<O, R> removeCommand;
    private final ListCommand<O, R> listCommand;
    private final QueryCommand<O, R> queryCommand;
    private final SingleItemCommand<O, R> singleItemCommand;
    CommandBasedList(@NotNull O owner,
                     @Nullable AddCommand<O, R> addCommand,
                     @Nullable RemoveCommand<O, R> removeCommand,
                     @Nullable ListCommand<O, R> listCommand,
                     @Nullable QueryCommand<O, R> queryCommand,
                     @Nullable SingleItemCommand<O, R> singleItemCommand) {
        this.owner = owner;
        this.addCommand = addCommand;
        this.removeCommand = removeCommand;
        this.listCommand = listCommand;
        this.queryCommand = queryCommand;
        this.singleItemCommand = singleItemCommand;
    }
    @NotNull
    public void add(final @NotNull R item) throws CommandExecutionException {
        assert addCommand != null;
        addCommand.setItem(item);
        owner.getYouTrack().execute(addCommand);
    }
    @NotNull
    public void remove(final @NotNull R item) throws CommandExecutionException {
        assert removeCommand != null;
        removeCommand.setItem(item);
        owner.getYouTrack().execute(removeCommand);
    }
    @Nullable
    public R item(final int index) throws CommandExecutionException {
        return this.list().get(index);
    }
    @Nullable
    public R item(final @NotNull String id) throws CommandExecutionException {
        assert singleItemCommand != null;
        singleItemCommand.setItemId(id);
        return owner.getYouTrack().execute(singleItemCommand).getResult();
    }
    @NotNull
    public List<R> list() throws CommandExecutionException {
        assert listCommand != null;
        final CommandResultItemList<R> result = owner.getYouTrack().execute(listCommand);
        return result.success() ? result.getResult() : Collections.<R>emptyList();
    }
    @NotNull
    public List<R> query(final @NotNull String query, final int start, final int maxResults) throws CommandExecutionException {
        assert queryCommand != null;
        queryCommand.addParameter("query", query);
        queryCommand.addParameter("max", String.valueOf(maxResults));
        queryCommand.addParameter("start", String.valueOf(start));
        final CommandResultItemList<R> result = owner.getYouTrack().execute(queryCommand);
        return result.success() ? result.getResult() : Collections.<R>emptyList();
    }
    public List<R> query(final @NotNull String query) throws CommandExecutionException {
        return query(query, 0, 100);
    }
}