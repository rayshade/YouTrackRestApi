package youtrack.commands.base;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import youtrack.BaseItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public abstract class SingleItemCommand<O extends BaseItem, R> extends Command<O, R> {

    protected String itemId;

    public SingleItemCommand(@NotNull O owner) {
        super(owner);
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "itemId='" + itemId + '\'' +
                '}';
    }
}
