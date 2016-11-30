package youtrack.issues.fields.values;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "value")
public abstract class BaseIssueFieldValue {
    @XmlValue
    private String value;
    BaseIssueFieldValue() {
    }
    BaseIssueFieldValue(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    void setValue(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "BaseIssueFieldValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
