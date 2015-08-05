package youtrack.commands.base;
import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public abstract class RunningCommand<O extends BaseItem, R> extends Command<O, R> {
    public RunningCommand(@NotNull O owner) {
        super(owner);
    }
    @Override
    public R getResult() throws CommandExecutionException, AuthenticationErrorException {
        return null;
    }
}