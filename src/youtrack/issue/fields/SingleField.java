package youtrack.issue.fields;

import youtrack.issue.fields.values.BaseIssueFieldValue;
import youtrack.issue.fields.values.IssueFieldValue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by egor.malyshev on 28.03.2014.
 */
@XmlRootElement
public class SingleField extends IssueField {
	@XmlElement(name = "value")
	private IssueFieldValue value;

	public SingleField() {
		super();
	}

	@Override
	public BaseIssueFieldValue getValue() {
		return value;
	}

	@Override
	public void setValue(BaseIssueFieldValue value) {
		this.value = (IssueFieldValue) value;
	}
}