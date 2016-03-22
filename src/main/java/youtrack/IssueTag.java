package youtrack;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by egor.malyshev on 07.04.2014.
 */
@XmlRootElement(name = "tag")
@XmlAccessorType(XmlAccessType.FIELD)
public class IssueTag extends BaseItem<Issue> {
    IssueTag() {
    }
    public static IssueTag createTag(String tag) {
        IssueTag issueTag = new IssueTag();
        issueTag.body = tag;
        issueTag.wrapper = true;
        return issueTag;
    }
    @Override
    public String toString() {
        return "IssueTag{" +
                "tag='" + body + '\'' +
                '}';
    }
    public String getTag() {
        return body;
    }
}
