package youtrack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public IssueAttachment() {
	}

	public static IssueAttachment createAttachment(String fileName) {
		IssueAttachment issueAttachment = new IssueAttachment();
		issueAttachment.url = fileName;
		return issueAttachment;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "IssueAttachment{" +
				"url='" + url + '\'' +
				", name='" + name + '\'' +
				'}';
	}

	public String getId() {

		Pattern extractor = Pattern.compile("\\?file=([0-9\\-]+)", Pattern.UNICODE_CASE);
		Matcher matcher = extractor.matcher(url);
		if (matcher.find()) {
			return matcher.group(1);
		} else return null;

	}
}