package youtrack;

import com.sun.istack.internal.NotNull;

/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
abstract class RunningCommand<O extends BaseItem, R> extends Command<O, R> {
    RunningCommand(@NotNull O owner) {
        super(owner);
    }

    @Override
    CommandResult<R> getResult() {
        return new CommandResult<R>(this);
    }
}