package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.http.util.EntityUtils;
import youtrack.exceptions.CommandExecutionException;


/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
abstract class SingleItemCommand<O extends BaseItem, R extends BaseItem<O>> extends Command<O, R> {
    String itemId;

    SingleItemCommand(@NotNull O owner) {
        super(owner);
    }

    @SuppressWarnings("unchecked")
    @Override
    public CommandResult<R> getResult() {
        final CommandResult<R> result = new CommandResult<R>(this);
        try {
            R item = null;
            final Object receivedData = objectFromXml(EntityUtils.toString(response.getEntity()));
            if (receivedData instanceof Error) {
                result.setStatus((Error) receivedData);
            } else {
                if (receivedData instanceof BaseItem) {
                    item = (R) receivedData;
                }
                if (receivedData instanceof BaseItemList) {
                    final BaseItemList<R> list = (BaseItemList<R>) receivedData;
                    for (final R listItem : list.getItems()) {
                        if (itemId.equals(listItem.getId())) {
                            item = listItem;
                            break;
                        }
                    }
                }
                if (item == null)
                    throw new CommandExecutionException(this, new Exception("\"" + itemId + "\" not found in " + owner.toString()));
                item.setOwner(owner);
                result.setResult(item);
            }
        } catch (CommandExecutionException e) {
            result.setException(e);
        } catch (Exception e) {
            result.setException(new CommandExecutionException(this, e));
        } finally {
            if (response != null) result.setStatus(response.getStatusLine());
        }
        return result;
    }

    void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "itemId='" + itemId + '\'' +
                '}';
    }
}