package youtrack.issues.fields;

import youtrack.issues.fields.values.IssueFieldValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Egor.Malyshev on 23.12.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CustomFieldValue extends BaseIssueField<List<IssueFieldValue>> {
    @XmlElement(name = "value")
    private List<IssueFieldValue> value;

    @XmlElement(name = "valueId")
    private List<IssueFieldValue> valueId;

    public CustomFieldValue() {
    }

    @Override
    public List<IssueFieldValue> getValue() {
        return valueId != null ? valueId : value;
    }

    @Override
    public void setValue(List<IssueFieldValue> value) {
        this.valueId = this.value = value;
    }

    @Override
    public String getStringValue() {
        final List<IssueFieldValue> values = getValue();
        return values == null || values.isEmpty() ? null : values.get(0).getValue();
    }
}