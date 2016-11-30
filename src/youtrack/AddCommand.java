package youtrack;

import com.sun.istack.internal.NotNull;

/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
abstract class AddCommand<O extends BaseItem, R> extends RunningCommand<O, R> {
    protected R item;

    AddCommand(@NotNull O owner) {
        super(owner);
    }

    public R getItem() {
        return item;
    }

    public void setItem(R item) {
        this.item = item;
    }

    @Override
    CommandResult<R> getResult() {
        return new CommandResult<R>(this);
    }
}
