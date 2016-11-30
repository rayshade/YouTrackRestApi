package youtrack.issues.fields;
import youtrack.issues.fields.values.IssueFieldValue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlRootElement
public class SprintField extends IssueField<IssueFieldValue> {
    @XmlElement(name = "value")
    private IssueFieldValue value;
    public SprintField() {
        super();
    }
    public static SprintField createField(String name, IssueFieldValue value) {
        SprintField field = new SprintField();
        field.name = name;
        field.value = value;
        return field;
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