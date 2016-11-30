package youtrack.issues.fields;
import youtrack.issues.fields.values.BaseIssueFieldValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by Egor.Malyshev on 19.12.13.
 */
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class IssueListField<V extends BaseIssueFieldValue> extends BaseIssueField<List<V>> {
    public IssueListField() {
    }
    @Override
    public String toString() {
        return "IssueListField{" +
                "name='" + name + '\'' +
                '}';
    }
}
