package youtrack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
@XmlRootElement(name = "tag")
@XmlAccessorType(XmlAccessType.FIELD)
public class IssueTag {
	@XmlValue
	private String tag;

	public IssueTag() {
	}

	public static IssueTag createTag(String tag) {
		IssueTag issueTag = new IssueTag();
		issueTag.tag = tag;
		return issueTag;
	}

	@Override
	public String toString() {
		return "IssueTag{" +
				"tag='" + tag + '\'' +
				'}';
	}

	public String getTag() {
		return tag;
	}
}
