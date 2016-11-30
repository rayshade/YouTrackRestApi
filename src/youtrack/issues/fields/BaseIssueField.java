package youtrack.issues.fields;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by Egor.Malyshev on 19.12.13.
 */
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseIssueField<T> {
    @XmlAttribute(name = "name")
    protected String name;
    public BaseIssueField() {
    }
    @Override
    public String toString() {
        return "BaseIssueField{" +
                "name='" + name + '\'' +
                '}';
    }
    public String getName() {
        return name;
    }
    public abstract T getValue();
    public abstract void setValue(T value);
    public abstract String getStringValue();
}
