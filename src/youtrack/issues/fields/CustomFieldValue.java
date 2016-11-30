package youtrack.issues.fields;
import youtrack.issues.fields.values.IssueFieldValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CustomFieldValue extends IssueField<IssueFieldValue> {
    @XmlElement(name = "value")
    private IssueFieldValue value;
    public CustomFieldValue() {
    }
    @Override
    public IssueFieldValue getValue() {
        return value;
    }
    @Override
    public void setValue(IssueFieldValue value) {
        this.value = value;
    }
    @Override
    public String getStringValue() {
        return value == null ? null : value.getValue();
    }
}