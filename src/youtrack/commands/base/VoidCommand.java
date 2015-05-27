package youtrack.commands.base;
import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;
/**
 * Created by Egor.Malyshev on 25.03.2015.
 */
public abstract class VoidCommand<O extends BaseItem, R> extends Command<O, R> {
    public VoidCommand(@NotNull O owner) {
        super(owner);
    }
}
