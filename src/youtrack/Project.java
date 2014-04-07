package youtrack;

import youtrack.commands.AddIssue;
import youtrack.commands.GetIssue;
import youtrack.commands.GetIssues;
import youtrack.commands.results.Result;
import youtrack.exceptions.NoSuchIssueFieldException;

import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {
	@XmlAttribute(name = "name")
	private String name;
	@XmlAttribute(name = "shortName")
	private String id;

	@XmlTransient
	private YouTrack youTrack;

	Project() {

	}

	@Override
	public String toString() {
		return "Project{" +
				"name='" + name + '\'' +
				", id='" + id + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	void setYouTrack(YouTrack youTrack) {
		this.youTrack = youTrack;
	}

	/**
	 * Retrieve a list of issues that match specific query.
	 *
	 * @return list of @link Issue instances or null if there was an error.
	 */

	public List<Issue> issues(String query) throws IOException, NoSuchIssueFieldException {

		Result result = youTrack.execute(new GetIssues(query));

		IssueList issueList = (IssueList) result.getData();

		if (issueList != null) {

			issueList.setYouTrack(this.youTrack);

			return issueList.getIssues();

		} else return null;

	}

	/**
	 * Retrieve a single issue by id
	 *
	 * @return @link Issue instance or null if there was an error.
	 */

	public Issue issue(String id) throws IOException, NoSuchIssueFieldException {

		Result result = youTrack.execute(new GetIssue(id));

		if (result.getData() != null) {

			Issue issue = (Issue) result.getData();

			issue.setYouTrack(this.youTrack);

			return issue;

		} else return null;

	}

	/**
	 * Creates a new issue.
	 *
	 * @return @link Issue instance or null if there was an error.
	 */

	public Issue create(String summary, String description) throws IOException, NoSuchIssueFieldException {

		Result result = youTrack.execute(new AddIssue(this.id, summary, description));

		if (result.success()) {

			return this.issue((String) result.getData());

		} else return null;

	}
}
