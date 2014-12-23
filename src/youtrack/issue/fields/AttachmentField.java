package youtrack.issue.fields;

import youtrack.issue.fields.values.AttachmentFieldValue;

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

}
