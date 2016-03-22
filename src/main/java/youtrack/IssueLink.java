package youtrack;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by egor.malyshev on 02.04.2014.
 */
@XmlRootElement(name = "issueLink")
@XmlAccessorType(XmlAccessType.FIELD)
public class IssueLink extends BaseItem<Issue> {
    @XmlAttribute(name = "typeInward")
    private String typeInward;
    @XmlAttribute(name = "typeOutward")
    private String typeOutward;
    @XmlAttribute(name = "typeName")
    private String typeName;
    @XmlAttribute(name = "target")
    private String target;
    @XmlAttribute(name = "source")
    private String source;
    IssueLink() {
    }
    public static IssueLink createLink(String target, LinkTypes relation) {
        IssueLink issueLink = new IssueLink();
        issueLink.target = target;
        issueLink.wrapper = true;
        switch(relation) {
            case DEPENDS_ON:
                issueLink.typeName = "depends on ";
                break;
            case DUPLICATES:
                issueLink.typeName = "duplicates ";
                break;
            case RELATES_TO:
                issueLink.typeName = "relates to ";
                break;
            case REQUIRED_FOR:
                issueLink.typeName = "required for";
                break;
            case PARENT_FOR:
                issueLink.typeName = "parent for";
                break;
            case SUBTASK_OF:
                issueLink.typeName = "subtask of";
        }
        return issueLink;
    }
    public String getTypeInward() {
        return typeInward;
    }
    public String getTypeOutward() {
        return typeOutward;
    }
    public String getTypeName() {
        return typeName;
    }
    @Override
    public String toString() {
        return "IssueLink{" +
                "typeInward='" + typeInward + '\'' +
                ", typeOutward='" + typeOutward + '\'' +
                ", typeName='" + typeName + '\'' +
                ", target='" + target + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
    public String getTarget() {
        return target;
    }
    public String getSource() {
        return source;
    }
    public enum LinkTypes {
        RELATES_TO,
        REQUIRED_FOR,
        DEPENDS_ON,
        DUPLICATES,
        SUBTASK_OF,
        PARENT_FOR
    }
}