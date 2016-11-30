package youtrack.issues.fields.values;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "value")
public class IssueFieldValue extends BaseIssueFieldValue {
    private IssueFieldValue() {
    }
    public IssueFieldValue(String value) {
        super(value);
    }
    public static IssueFieldValue createValue(String value) {
        IssueFieldValue issueFieldValue = new IssueFieldValue();
        issueFieldValue.setValue(value);
        return issueFieldValue;
    }
}
