package youtrack;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
abstract class RemoveCommand<O extends BaseItem, R> extends Command<O, R> {
    protected R item;

    RemoveCommand(@NotNull O owner) {
        super(owner);
    }

    void setItem(R item) {
        this.item = item;
    }
}