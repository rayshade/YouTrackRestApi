package youtrack.commands.base;
import youtrack.BaseItem;
import youtrack.BaseItemList;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.util.Service;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
public abstract class ListCommand<O extends BaseItem, R extends BaseItem<O>> extends Command<O, List<R>> {
    public ListCommand(@Nonnull O owner) {
        super(owner);
    }
    @Nonnull
    @Override
    public List<R> getResult() throws AuthenticationErrorException, CommandExecutionException {
        List<R> list;
        try {
            final String responseBodyAsString = Service.readStream(method.getResponseBodyAsStream());
            @SuppressWarnings("unchecked")
            final BaseItemList<R> resultList = (BaseItemList<R>) objectFromXml(responseBodyAsString);
            list = resultList.getItems();
            if(list == null) return Collections.emptyList();
            for(final R item : list) {
                item.setOwner(owner);
            }
        } catch(CommandExecutionException e) {
            throw e;
        } catch(Exception e) {
            throw new CommandExecutionException(this, e);
        }
        return list;
    }
}