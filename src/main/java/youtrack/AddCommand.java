package youtrack;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
abstract class AddCommand<O extends BaseItem, R> extends Command<O, R> {
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
}
