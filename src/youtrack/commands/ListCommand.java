package youtrack.commands;

import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;

import java.util.List;

/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
public abstract class ListCommand<O extends BaseItem, R> extends Command<O, List<R>> {
    public ListCommand(@NotNull O owner) {
        super(owner);
    }
}
