package youtrack.commands.base;

import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;

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
        return this.getClass().getSimpleName()+"{" +
                "itemId='" + itemId + '\'' +
                '}';
    }
}
