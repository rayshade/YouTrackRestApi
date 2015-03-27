package youtrack;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import youtrack.commands.base.*;
import youtrack.exceptions.CommandExecutionException;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by egor.malyshev on 03.04.2014.
 * <p/>
 * Operates with items of R owned by items of O.
 * For example, CommandBasedList<Project, Issue> allows handling issues from project.
 */

public class CommandBasedList<O extends BaseItem, R extends BaseItem> {
    private final ThreadLocal<O> owner;
    private final ThreadLocal<AddCommand<O, R>> addCommand;
    private final ThreadLocal<RemoveCommand<O, R>> removeCommand;
    private final ThreadLocal<ListCommand<O, R>> listCommand;
    private final ThreadLocal<QueryCommand<O, R>> queryCommand;
    private final ThreadLocal<SingleItemCommand<O, R>> singleItemCommand;

    CommandBasedList(final @NotNull O owner,
                     final @Nullable AddCommand<O, R> addCommand,
                     final @Nullable RemoveCommand<O, R> removeCommand,
                     final @Nullable ListCommand<O, R> listCommand,
                     final @Nullable QueryCommand<O, R> queryCommand,
                     final @Nullable SingleItemCommand<O, R> singleItemCommand) {

        this.owner = ThreadLocal.withInitial(new Supplier<O>() {
            @Override
            public O get() {
                return owner;
            }
        });
        this.addCommand = ThreadLocal.withInitial(new Supplier<AddCommand<O, R>>() {
            @Override
            public AddCommand<O, R> get() {
                return addCommand;
            }
        });
        this.removeCommand = ThreadLocal.withInitial(new Supplier<RemoveCommand<O, R>>() {
            @Override
            public RemoveCommand<O, R> get() {
                return removeCommand;
            }
        });
        this.listCommand = ThreadLocal.withInitial(new Supplier<ListCommand<O, R>>() {
            @Override
            public ListCommand<O, R> get() {
                return listCommand;
            }
        });
        this.queryCommand = ThreadLocal.withInitial(new Supplier<QueryCommand<O, R>>() {
            @Override
            public QueryCommand<O, R> get() {
                return queryCommand;
            }
        });
        this.singleItemCommand = ThreadLocal.withInitial(new Supplier<SingleItemCommand<O, R>>() {
            @Override
            public SingleItemCommand<O, R> get() {
                return singleItemCommand;
            }
        });
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

    @NotNull
    public List<R> query(final @NotNull String query) throws CommandExecutionException {
        return query(query, 0, 100);
    }
}