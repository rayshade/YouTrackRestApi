package youtrack;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "comments")
public class CommentList extends BaseItemList<IssueComment> {
    @XmlElement(name = "comment")
    private List<IssueComment> comments;
    CommentList() {
    }
    @Override
    public List<IssueComment> getItems() {
        return comments;
    }
    @Override
    public String toString() {
        return "CommentList{" +
                "comments=" + comments +
                '}';
    }
}