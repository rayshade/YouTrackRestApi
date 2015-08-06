package youtrack;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by egor.malyshev on 07.04.2014.
 */
@XmlRootElement(name = "issueTags")
@XmlAccessorType(XmlAccessType.FIELD)
public class TagList extends BaseItemList<IssueTag> {
    @XmlElement(name = "tag")
    private List<IssueTag> tags;
    TagList() {
    }
    @Override
    public List<IssueTag> getItems() {
        return tags;
    }
}
