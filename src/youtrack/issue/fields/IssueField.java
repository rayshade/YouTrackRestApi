package youtrack.issue.fields;

import youtrack.issue.fields.values.BaseIssueFieldValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Egor.Malyshev on 19.12.13.
 */
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class IssueField {
	@XmlAttribute(name = "name")
	private String name;

	public IssueField() {
	}

	@Override
	public String toString() {
		return "IssueField{" +
				"name='" + name + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public abstract BaseIssueFieldValue getValue();

	public abstract void setValue(BaseIssueFieldValue value);
}
