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
    private final ThreadLocal<O> owner = new ThreadLocal<O>();
    private final ThreadLocal<AddCommand<O, R>> addCommand = new ThreadLocal<AddCommand<O, R>>();
    private final ThreadLocal<RemoveCommand<O, R>> removeCommand = new ThreadLocal<RemoveCommand<O, R>>();
    private final ThreadLocal<ListCommand<O, R>> listCommand = new ThreadLocal<ListCommand<O, R>>();
    private final ThreadLocal<QueryCommand<O, R>> queryCommand = new ThreadLocal<QueryCommand<O, R>>();
    private final ThreadLocal<SingleItemCommand<O, R>> singleItemCommand = new ThreadLocal<SingleItemCommand<O, R>>();

    CommandBasedList(@NotNull O owner,
                     @Nullable AddCommand<O, R> addCommand,
                     @Nullable RemoveCommand<O, R> removeCommand,
                     @Nullable ListCommand<O, R> listCommand,
                     @Nullable QueryCommand<O, R> queryCommand,
                     @Nullable SingleItemCommand<O, R> singleItemCommand) {
        this.owner.set(owner);
        this.addCommand.set(addCommand);
        this.removeCommand.set(removeCommand);
        this.listCommand.set(listCommand);
        this.queryCommand.set(queryCommand);
        this.singleItemCommand.set(singleItemCommand);
    }

    public void add(final @NotNull R item) throws CommandExecutionException {
        final AddCommand<O, R> addCommand = this.addCommand.get();
        assert addCommand != null;
        addCommand.setItem(item);
        owner.get().getYouTrack().execute(addCommand);
    }

    public void remove(final @NotNull R item) throws CommandExecutionException {
        final RemoveCommand<O, R> removeCommand = this.removeCommand.get();
        assert removeCommand != null;
        removeCommand.setItem(item);
        owner.get().getYouTrack().execute(removeCommand);
    }

    @Nullable
    public R item(final int index) throws CommandExecutionException {
        final List<R> result = query("", index, 1);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Nullable
    public R item(final @NotNull String id) throws CommandExecutionException {
        final SingleItemCommand<O, R> singleItemCommand = this.singleItemCommand.get();
        assert singleItemCommand != null;
        singleItemCommand.setItemId(id);
        return owner.get().getYouTrack().execute(singleItemCommand).getResult();
    }

    @NotNull
    public List<R> list() throws CommandExecutionException {
        final ListCommand<O, R> listCommand = this.listCommand.get();
        assert listCommand != null;
        final CommandResultItemList<R> result = owner.get().getYouTrack().execute(listCommand);
        return result.success() ? result.getResult() : Collections.<R>emptyList();
    }

    @NotNull
    public List<R> query(final @NotNull String query, final int start, final int maxResults) throws CommandExecutionException {
        final QueryCommand<O, R> queryCommand = this.queryCommand.get();
        assert queryCommand != null;
        queryCommand.addParameter("query", query);
        queryCommand.addParameter("max", String.valueOf(maxResults));
        queryCommand.addParameter("start", String.valueOf(start));
        final CommandResultItemList<R> result = owner.get().getYouTrack().execute(queryCommand);
        return result.success() ? result.getResult() : Collections.<R>emptyList();
    }
}