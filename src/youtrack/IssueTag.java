package youtrack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
@XmlRootElement(name="tag")
@XmlAccessorType(XmlAccessType.FIELD)
public class IssueTag {
	private String tag;

	public String getTag() {
		return tag;
	}
}
