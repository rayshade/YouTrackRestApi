package youtrack.commands.base;

import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;
import youtrack.commands.util.QueryParameters;

/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public abstract class QueryCommand<O extends BaseItem, R> extends ListCommand<O, R> {
    protected QueryParameters parameters;

    public QueryCommand(@NotNull O owner) {
        super(owner);
    }

    public void setParameters(QueryParameters parameters) {
        this.parameters = parameters;
    }
}