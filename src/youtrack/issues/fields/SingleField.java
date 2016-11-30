package youtrack.issues.fields;
import youtrack.issues.fields.values.IssueFieldValue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlRootElement
public class SingleField extends IssueField<IssueFieldValue> {
    @XmlElement(name = "value")
    private IssueFieldValue value;
    public SingleField() {
        super();
    }
    public static SingleField createField(String name, IssueFieldValue value) {
        SingleField field = new SingleField();
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