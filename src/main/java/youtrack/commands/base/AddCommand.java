package youtrack.commands.base;
import youtrack.BaseItem;

import javax.annotation.Nonnull;

/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
public abstract class AddCommand<O extends BaseItem, R> extends VoidCommand<O, R> {
    protected R item;
    public AddCommand(@Nonnull O owner) {
        super(owner);
    }
    public R getItem() {
        return item;
    }
    public void setItem(R item) {
        this.item = item;
    }
}
