package youtrack.commands.base;
import com.sun.istack.internal.NotNull;
import youtrack.BaseItem;
import youtrack.ItemList;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.util.Service;
/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public abstract class SingleItemCommand<O extends BaseItem, R extends BaseItem<O>> extends Command<O, R> {
    protected String itemId;
    public SingleItemCommand(@NotNull O owner) {
        super(owner);
    }
    @SuppressWarnings("unchecked")
    @Override
    public R getResult() throws CommandExecutionException, AuthenticationErrorException {
        R result = null;
        try {
            final String responseBodyAsString = Service.readStream(method.getResponseBodyAsStream());
            final Object receivedData = objectFromXml(responseBodyAsString);
            if(receivedData instanceof BaseItem) {
                result = (R) receivedData;
            }
            if(receivedData instanceof ItemList) {
                ItemList<R> list = (ItemList<R>) receivedData;
                for(final R item : list.getItems()) {
                    if(itemId.equals(item.getId())) {
                        result = item;
                        break;
                    }
                }
            }
            if(result == null)
                throw new CommandExecutionException(this, new Exception("Cannot get \"" + itemId + "\"+ from " + owner.toString()));
            result.setOwner(owner);
        } catch(CommandExecutionException e) {
            throw e;
        } catch(Exception e) {
            throw new CommandExecutionException(this, e);
        }
        return result;
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