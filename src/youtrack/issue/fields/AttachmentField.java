package youtrack.issue.fields;

import youtrack.issue.fields.values.AttachmentFieldValue;
import youtrack.issue.fields.values.BaseIssueFieldValue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlRootElement
public class AttachmentField extends IssueField {

	@XmlElement(name = "value")
	private List<AttachmentFieldValue> attachments;

	public AttachmentField() {
	}

	public List<AttachmentFieldValue> getAttachments() {
		return attachments;
	}

	@Override
	public AttachmentFieldValue getValue() {

		return null;
	}

	@Override
	public void setValue(BaseIssueFieldValue value) {

	}
}
