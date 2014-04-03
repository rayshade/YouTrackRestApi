package youtrack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class IssueList {
	@XmlElement(name = "issue")
	private List<Issue> issues;

	IssueList() {
	}

	@Override
	public String toString() {
		return "IssueList{" +
				"issues=" + issues +
				'}';
	}

	List<Issue> getIssues() {

		return issues;
	}

	void setYouTrack(YouTrack youTrack) {
		for (Issue issue : issues) {
			issue.setYouTrack(youTrack);
		}
	}
}