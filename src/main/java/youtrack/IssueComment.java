package youtrack;
import javax.xml.bind.annotation.*;
import java.util.List;
/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "comment")
public class IssueComment extends BaseItem<Issue> {
    @XmlAttribute(name = "issueId")
    private String issueId;
    @XmlAttribute(name = "author")
    private String author;
    @XmlAttribute(name = "deleted")
    private Boolean deleted;
    @XmlAttribute(name = "shownForIssueAuthor")
    private Boolean shownForIssueAuthor;
    @XmlAttribute(name = "created")
    private Long created;
    @XmlAttribute(name = "text")
    private String text;
    @XmlAttribute(name = "authorFullName")
    private String authorFullName;
    @XmlAttribute(name = "updated")
    private Long updated;
    @XmlElementWrapper(name = "replies")
    @XmlElement(name = "comment")
    private List<IssueComment> replies;
    private IssueComment() {
    }
    public static IssueComment createComment(String commentText) {
        IssueComment issueComment = new IssueComment();
        issueComment.text = commentText;
        issueComment.wrapper = true;
        return issueComment;
    }
    public String getIssueId() {
        return issueId;
    }
    public String getAuthor() {
        return author;
    }
    public Boolean getDeleted() {
        return deleted;
    }
    public Boolean getShownForIssueAuthor() {
        return shownForIssueAuthor;
    }
    public Long getCreated() {
        return created;
    }
    public List<IssueComment> getReplies() {
        return replies;
    }
    public String getText() {
        return text;
    }
    public String getAuthorFullName() {
        return authorFullName;
    }
    @Override
    public String toString() {
        return "IssueComment{" +
                "id='" + getId() + '\'' +
                ", issueId='" + issueId + '\'' +
                ", author='" + author + '\'' +
                ", deleted=" + deleted +
                ", shownForIssueAuthor=" + shownForIssueAuthor +
                ", created=" + created +
                ", text='" + text + '\'' +
                ", authorFullName='" + authorFullName + '\'' +
                ", updated=" + updated +
                '}';
    }
}
