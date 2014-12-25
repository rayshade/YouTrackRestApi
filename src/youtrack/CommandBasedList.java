package youtrack;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import youtrack.commands.*;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.CommandNotAvailableException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor.malyshev on 03.04.2014.
 * <p/>
 * Operates with items of R owned by items of O.
 * For example, CommandBasedList<Project, Issue> allows handling issues from project.
 */

public class CommandBasedList<O extends BaseItem, R extends BaseItem> {
    private final List<R> EMPTY_RESULT = new ArrayList<R>();
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
    public CommandResult<R> add(final @NotNull R item) throws IOException, NoSuchIssueFieldException, CommandNotAvailableException, CommandExecutionException {
        assert addCommand != null;
        addCommand.setItem(item);
        return owner.getYouTrack().execute(addCommand);
    }

    @NotNull
    public CommandResult<R> remove(final @NotNull R item) throws IOException, NoSuchIssueFieldException, CommandNotAvailableException, CommandExecutionException {
        assert removeCommand != null;
        removeCommand.setItem(item);
        return owner.getYouTrack().execute(removeCommand);
    }

    @Nullable
    public R item(final int index) throws CommandNotAvailableException, CommandExecutionException, NoSuchIssueFieldException, IOException {
        return this.list().get(index);
    }

    @Nullable
    public R item(final @NotNull String id) throws CommandNotAvailableException, CommandExecutionException, NoSuchIssueFieldException, IOException {
        assert singleItemCommand != null;
        singleItemCommand.setItemId(id);
        return owner.getYouTrack().execute(addCommand).getData();
    }

    @NotNull
    public List<R> list() throws CommandNotAvailableException, IOException, NoSuchIssueFieldException, CommandExecutionException {
        assert listCommand != null;
        final CommandResult<List<R>> result = owner.getYouTrack().execute(listCommand);
        return result.success() ? result.getData() : EMPTY_RESULT;
    }

    @NotNull
    public List<R> query(final @NotNull String query, final int start, final int maxResults) throws CommandNotAvailableException, NoSuchIssueFieldException, IOException, CommandExecutionException {
        assert queryCommand != null;
        queryCommand.setParameters(new QueryParameters(query, start, maxResults));
        final CommandResult<List<R>> result = owner.getYouTrack().execute(queryCommand);
        return result.success() ? result.getData() : EMPTY_RESULT;
    }

    public List<R> query(final @NotNull String query) throws CommandNotAvailableException, NoSuchIssueFieldException, IOException, CommandExecutionException {
        return query(query, 0, 100);
    }
}