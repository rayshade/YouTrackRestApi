package youtrack;

import youtrack.commands.*;
import youtrack.commands.results.Result;
import youtrack.exceptions.NoSuchIssueFieldException;
import youtrack.exceptions.SetIssueFieldException;
import youtrack.issue.fields.IssueField;
import youtrack.issue.fields.values.BaseIssueFieldValue;
import youtrack.issue.fields.values.IssueFieldValue;
import youtrack.issue.fields.values.MultiUserFieldValue;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Egor.Malyshev on 19.12.13.
 */
@XmlRootElement(name = "issue")
@XmlAccessorType(XmlAccessType.FIELD)
public class Issue {
	@XmlTransient
	public CommandBasedList<IssueComment> comments;
	/*@XmlTransient
	public CommandBasedList<IssueAttachment> attachments;*/
	@XmlTransient
	public CommandBasedList<IssueLink> links;

	@XmlAttribute(name = "id")
	private String id;
	@XmlElement(name = "field")
	private List<IssueField> fieldArray;

	/*
	This is used to work around the issue with JAXB not being able to unmarshal a Map.
	 */
	@XmlElement(name = "tag")
	private String tag;
	@XmlTransient
	private HashMap<String, IssueField> fields;
	@XmlTransient
	private YouTrack youTrack;

	Issue() {
		comments = new CommandBasedList<IssueComment>(this, AddComment.class, RemoveComment.class, GetIssueComments.class);
//		attachments = new CommandBasedList<IssueComment>(this, AddComment.class, RemoveComment.class, GetIssueComments.class);
		links = new CommandBasedList<IssueLink>(this, AddIssueLink.class, RemoveIssueLink.class, GetIssueLinks.class);
	}

	public void setFieldByName(String fieldName, BaseIssueFieldValue value) throws SetIssueFieldException {

		if (fields.containsKey(fieldName)) {

			Result result = youTrack.execute(new ModifyIssueField(this, fields.get(fieldName), value));

			if (result.getResponseCode() != 200) {

				youtrack.commands.results.Error error = (youtrack.commands.results.Error) result.getData();

				throw new SetIssueFieldException(error.getErrorMessage());
			}

			fields.get(fieldName).setValue(value);
		}
	}

	public BaseIssueFieldValue getFieldByName(String fieldName) throws NoSuchIssueFieldException {

		if (fields.containsKey(fieldName)) {

			updateSelf();

			return fields.get(fieldName).getValue();

		} else throw new NoSuchIssueFieldException(fieldName);

	}

	@SuppressWarnings("UnusedDeclaration")
	protected void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		fields = new HashMap<String, IssueField>();
		for (IssueField issueField : fieldArray) {
			fields.put(issueField.getName(), issueField);
		}
	}

	public String getTag() {
		return tag;
	}

/*
	public List<IssueComment> getComments() {
		Result result = youTrack.execute(new GetIssueComments(this));

		CommentList commentList = (CommentList) result.getData();

		return (commentList == null) ? null : commentList.getComments();
	}

	public boolean removeComment(IssueComment comment) {

		Result result = youTrack.execute(new RemoveComment(this, comment));

		return (result.success());
	}

	public IssueComment addComment(String text) {

		Result result = youTrack.execute(new AddComment(this, IssueComment.createComment(text)));

		if (result.success()) {

			result = youTrack.execute(new GetIssueComments(this));

			if (result.success()) {

				List<IssueComment> comments = this.getComments();
				return comments.get(comments.size() - 1);
			}

		}

		return null;
	}
*/

	@Override
	public String toString() {
		return "Issue{" +
				"id='" + id + '\'' +
				", fieldArray=" + fieldArray +
				", tag='" + tag + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public boolean isResolved() {
		return fields.containsKey("resolved");
	}

	public String getState() {
		try {
			return getFieldByName("State").getValue();
		} catch (NoSuchIssueFieldException e) {
			return null;
		}
	}

	public String getDescription() {
		try {
			updateSelf();
			return getFieldByName("description").getValue();
		} catch (NoSuchIssueFieldException e) {
			return null;
		}
	}

	public String getSummary() {
		try {
			return getFieldByName("summary").getValue();
		} catch (NoSuchIssueFieldException e) {
			return null;
		}
	}

	public int getVotes() {
		try {
			return Integer.parseInt(getFieldByName("votes").getValue());
		} catch (Exception e) {
			return 0;
		}
	}

	public String getType() {
		try {
			return getFieldByName("Type").getValue();
		} catch (NoSuchIssueFieldException e) {
			return null;
		}
	}

	public String getPriority() {
		try {
			return getFieldByName("Priority").getValue();
		} catch (NoSuchIssueFieldException e) {
			return null;
		}
	}

	public MultiUserFieldValue getAssignee() {
		try {
			return (MultiUserFieldValue) getFieldByName("Assignee");
		} catch (NoSuchIssueFieldException e) {
			return null;
		}
	}

	public String getReporter() {
		try {
			return getFieldByName("reporterName").getValue();
		} catch (NoSuchIssueFieldException e) {
			return null;
		}

	}

	public boolean setState(String state) {
		try {
			setFieldByName("State", new IssueFieldValue(state));
			return true;
		} catch (SetIssueFieldException e) {
			return false;
		}
	}

	public boolean setType(String type) {
		try {
			setFieldByName("Type", new IssueFieldValue(type));
			return true;
		} catch (SetIssueFieldException e) {
			return false;
		}
	}

	public boolean setPriority(String priority) {
		try {
			setFieldByName("Priority", new IssueFieldValue(priority));
			return true;
		} catch (SetIssueFieldException e) {
			return false;
		}
	}

	public boolean setAssignee(String assignee, String fullName) {
		try {
			MultiUserFieldValue value = new MultiUserFieldValue(assignee);
			value.setFullName(fullName);
			setFieldByName("Assignee", value);
			return true;
		} catch (SetIssueFieldException e) {
			return false;
		}
	}

	public boolean setSummary(String summary) {

		Result result = youTrack.execute(new ModifyIssue(this, summary, null));

		if (result.success()) {

			fields.get("summary").setValue(new IssueFieldValue(summary));
			return true;

		} else return false;
	}

	public boolean setDescription(String description) {

		Result result = youTrack.execute(new ModifyIssue(this, null, description));

		if (result.success()) {

			fields.get("description").setValue(new IssueFieldValue(description));
			return true;

		} else return false;

	}

/*
	public List<IssueLink> links() {

		Result result = youTrack.execute(new GetIssueLinks(this));

		if (result.success()) {
			return ((LinkList) result.getData()).getLinks();
		} else return null;
	}

	public IssueLink addLink(Issue target, IssueLink.LinkTypes relation) {

		return this.addLink(target.getId(), relation);

	}

	public IssueLink addLink(String target, IssueLink.LinkTypes relation) {

		Result result = youTrack.execute(new AddIssueLink(this, IssueLink.createLink(target, relation)));


		if (result.success()) {

			List<IssueLink> links = this.links();

			return links.get(links.size() - 1);

		} else return null;

	}

	public boolean removeLink(IssueLink link) {

		Result result = youTrack.execute(new RemoveIssueLink(this, link));

		return result.success();
	}
*/

	YouTrack getYouTrack() {
		return youTrack;
	}

	void setYouTrack(YouTrack youTrack) {
		this.youTrack = youTrack;
	}

	public boolean vote() {

		return youTrack.execute(new ChangeIssueVotes(this, true)).success();
	}

	public boolean unVote() {

		return youTrack.execute(new ChangeIssueVotes(this, false)).success();
	}

	private boolean updateSelf() {
		Issue issue = (Issue) youTrack.execute(new GetIssue(this.id)).getData();
		if (issue != null) {
			this.tag = issue.tag;
			this.fields.clear();
			this.fields.putAll(issue.fields);
			return true;
		} else return false;
	}

	public IssueAttachment addAttachment(String fileName) {
		return null;
	}

	public boolean removeAttachment(IssueAttachment attachment) {
		return false;
	}

	public List<IssueAttachment> attachments() {
		return null;
	}

}