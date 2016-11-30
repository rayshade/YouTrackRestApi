package youtrack.issues.fields;
import youtrack.issues.fields.values.BaseIssueFieldValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by Egor.Malyshev on 19.12.13.
 */
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class IssueField<V extends BaseIssueFieldValue> extends BaseIssueField<V> {
}
