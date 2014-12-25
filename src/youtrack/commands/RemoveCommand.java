package youtrack.commands;

import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;
import youtrack.exceptions.CommandExecutionException;

/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
public abstract class RemoveCommand<O extends BaseItem, R> extends Command<O, R> {
    protected R item;

    public RemoveCommand(@NotNull O owner) {
        super(owner);
    }

    public void setItem(R item) {
        this.item = item;
    }

    @Override
    public R getResult() throws CommandExecutionException {
        return null;
    }
}
