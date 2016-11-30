package youtrack.issues.fields;
import youtrack.issues.fields.values.AttachmentFieldValue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlRootElement
public class AttachmentField extends IssueListField<AttachmentFieldValue> {
    @XmlElement(name = "value")
    private List<AttachmentFieldValue> attachments;
    public AttachmentField() {
    }
    @Override
    public List<AttachmentFieldValue> getValue() {
        return attachments;
    }
    @Override
    public void setValue(List<AttachmentFieldValue> value) {
        attachments = value;
    }
    @Override
    public String getStringValue() {
        final StringBuilder sb = new StringBuilder();
        for(final AttachmentFieldValue value : attachments) {
            sb.append(value.getUrl()).append(" ");
        }
        return sb.toString();
    }
}
