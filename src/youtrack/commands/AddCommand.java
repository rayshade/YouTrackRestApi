package youtrack.commands;

import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;

/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
public abstract class AddCommand<O extends BaseItem, R> extends Command<O, R> {
    protected R item;

    public AddCommand(@NotNull O owner) {
        super(owner);
    }

    public R getItem() {
        return item;
    }

    public void setItem(R item) {
        this.item = item;
    }
}
