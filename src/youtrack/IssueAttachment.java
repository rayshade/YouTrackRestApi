package youtrack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
@XmlRootElement(name = "fileUrl")
@XmlAccessorType(XmlAccessType.FIELD)
public class IssueAttachment {

	@XmlAttribute(name = "url")
	private String url;

	@XmlAttribute(name = "name")
	private String name;

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public IssueAttachment() {
	}

	public static IssueAttachment createAttachment(String fileName) {
		IssueAttachment issueAttachment = new IssueAttachment();
		issueAttachment.url = fileName;
		return issueAttachment;
	}
}