package youtrack;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlRootElement(name = "issues")
public class IssueProjectList extends BaseItemList<Issue> {
    @XmlElement(name = "issue")
    protected List<Issue> items;
    IssueProjectList() {
    }
    @Override
    public List<Issue> getItems() {
        return items;
    }
}
