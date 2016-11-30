package youtrack.issues.fields.values;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
/**
 * Created by egor.malyshev on 29.03.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MultiUserFieldValue extends BaseIssueFieldValue {
    @XmlAttribute(name = "fullName")
    private String fullName;
    public MultiUserFieldValue() {
        super();
    }
    public MultiUserFieldValue(String value) {
        super(value);
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    @Override
    public String toString() {
        return "MultiUserFieldValue{" +
                "fullName='" + fullName + '\'' +
                '}';
    }
}