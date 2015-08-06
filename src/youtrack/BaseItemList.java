package youtrack;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseItemList<T extends BaseItem> {
    /*
       This is used to work around the issue with JAXB not being able to unmarshal a Map.
    */
    @XmlTransient
    protected Map<String, T> itemsMap;
    BaseItemList() {
    }
    public abstract List<T> getItems();
    @SuppressWarnings("UnusedDeclaration")
    private void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        itemsMap = new HashMap<String, T>();
        final List<T> items = getItems();
        if(items != null)
            for(T item : items) {
                itemsMap.put(item.getId(), item);
            }
    }
    @Nullable
    public T getItemById(final @NotNull String id) {
        return itemsMap == null ? null : itemsMap.get(id);
    }
}