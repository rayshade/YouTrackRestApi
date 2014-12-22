package youtrack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ItemList<T extends Item> {

    ItemList() {
    }

    public abstract List<T> getItems();

}