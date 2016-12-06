package youtrack;

import com.sun.istack.internal.NotNull;
import org.apache.http.util.EntityUtils;
import youtrack.exceptions.CommandExecutionException;

import java.util.Collections;
import java.util.List;

/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
abstract class ListCommand<O extends BaseItem, R extends BaseItem<O>> extends Command<O, List<R>> {
    ListCommand(@NotNull O owner) {
        super(owner);
    }

    @NotNull
    @Override
    CommandResult<List<R>> getResult() {
        final CommandResult<List<R>> result = new CommandResult<List<R>>(this);
        if (response != null) {
            try {
                final Object resultList = objectFromXml(EntityUtils.toString(response.getEntity()));
                if (resultList instanceof Error) {
                    result.setStatus((Error) resultList);
                } else {
                    if (resultList instanceof BaseItemList) {
                        @SuppressWarnings("unchecked") final List<R> list = ((BaseItemList) resultList).getItems();
                        if (list != null) {
                            for (final R item : list) {
                                item.setOwner(owner);
                            }
                            result.setResult(list);
                        } else result.setResult(Collections.<R>emptyList());
                    }
                }
            } catch (CommandExecutionException e) {
                result.setException(e);
            } catch (Exception e) {
                result.setException(new CommandExecutionException(this, e));
            } finally {
                if (response != null) result.setStatus(response.getStatusLine());
            }
        }
        return result;
    }
}