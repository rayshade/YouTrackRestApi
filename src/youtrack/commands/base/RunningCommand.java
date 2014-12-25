package youtrack.commands.base;

import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;
import youtrack.exceptions.CommandExecutionException;

import java.util.Map;

/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public abstract class RunningCommand<O extends BaseItem, R> extends Command<O, R> {
    protected Map<String, R> arguments;

    public RunningCommand(@NotNull O owner) {
        super(owner);
    }

    @Override
    public R getResult() throws CommandExecutionException {
        return null;
    }

    public Map<String, R> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, R> arguments) {
        this.arguments = arguments;
    }
}