package youtrack.issues.fields.values;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
/**
 * Created by egor.malyshev on 30.03.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LinkFieldValue extends BaseIssueFieldValue {
    @XmlAttribute(name = "type")
    private String type;
    @XmlAttribute(name = "role")
    private String role;
    public LinkFieldValue() {
    }
    public LinkFieldValue(String value) {
        super(value);
    }
    @Override
    public String toString() {
        return "LinkFieldValue{" +
                "type='" + type + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
