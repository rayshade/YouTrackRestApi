package youtrack;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by egor.malyshev on 02.04.2014.
 */
@XmlRootElement(name = "issueLinks")
@XmlAccessorType(XmlAccessType.FIELD)
public class LinkList extends BaseItemList<IssueLink> {
    @XmlElement(name = "issueLink")
    private List<IssueLink> links;
    LinkList() {
    }
    @Override
    public List<IssueLink> getItems() {
        return links;
    }
}
