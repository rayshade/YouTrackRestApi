package youtrack.commands.base;

import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;

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
    public R getResult() throws Exception {
        return null;
    }
}
