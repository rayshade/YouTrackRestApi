package youtrack.issues.fields;
import youtrack.issues.fields.values.LinkFieldValue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlRootElement
public class LinkField extends IssueListField<LinkFieldValue> {
    @XmlElement(name = "value")
    private List<LinkFieldValue> links;
    public LinkField() {
    }
    @Override
    public List<LinkFieldValue> getValue() {
        return links;
    }
    @Override
    public void setValue(List<LinkFieldValue> value) {
        links = value;
    }
    @Override
    public String getStringValue() {
        final StringBuilder sb = new StringBuilder();
        for(final LinkFieldValue value : links) {
            sb.append(value.getRole()).append(":").append(value.getType()).append(" ");
        }
        return sb.toString();
    }
}