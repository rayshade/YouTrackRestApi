package youtrack;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlRootElement(name = "issueCompacts")
public class IssueCompactList extends BaseItemList<Issue> {
    @XmlElement(name = "issue")
    protected List<Issue> items;
    public IssueCompactList() {
    }
    @Override
    public List<Issue> getItems() {
        return items;
    }
}
