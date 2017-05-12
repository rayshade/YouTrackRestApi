package youtrack;

/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
abstract class RemoveCommand<O extends BaseItem, R> extends RunningCommand<O, R> {
    protected R item;

    RemoveCommand(O owner) {
        super(owner);
    }

    void setItem(R item) {
        this.item = item;
    }

    @Override
    CommandResult<R> getResult() {
        return new CommandResult<R>(this);
    }
}