package youtrack.commands.base;
import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;
/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public abstract class QueryCommand<O extends BaseItem, R extends BaseItem<O>> extends ListCommand<O, R> {
    public QueryCommand(@NotNull O owner) {
        super(owner);
    }
}