package youtrack.issues.fields;
import youtrack.issues.fields.values.MultiUserFieldValue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlRootElement
public class MultiUserField extends IssueField<MultiUserFieldValue> {
    @XmlElement(name = "value")
    private MultiUserFieldValue value;
    public MultiUserField() {
    }
    @Override
    public MultiUserFieldValue getValue() {
        return this.value;
    }
    @Override
    public void setValue(MultiUserFieldValue value) {
        this.value = value;
    }
    @Override
    public String getStringValue() {
        return value == null ? null : value.getFullName();
    }
}