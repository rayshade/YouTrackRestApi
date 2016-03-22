package youtrack.commands.base;
import youtrack.BaseItem;

import javax.annotation.Nonnull;

/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public abstract class QueryCommand<O extends BaseItem, R extends BaseItem<O>> extends ListCommand<O, R> {
    public QueryCommand(@Nonnull O owner) {
        super(owner);
    }
}