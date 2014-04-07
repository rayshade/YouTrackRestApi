package youtrack.exceptions;

import youtrack.Issue;
import youtrack.issue.fields.IssueField;
import youtrack.issue.fields.values.BaseIssueFieldValue;

/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class SetIssueFieldException extends Exception {

	private final Issue issue;
	private final IssueField issueField;
	private final BaseIssueFieldValue value;

	public SetIssueFieldException(Issue issue, IssueField issueField, BaseIssueFieldValue value) {
		super("Cannot set value of " + issueField.getName() + " in " + issue.getId() + " to " + value);
		this.issue = issue;
		this.issueField = issueField;
		this.value = value;
	}

	public Issue getIssue() {
		return issue;
	}

	public BaseIssueFieldValue getValue() {
		return value;
	}

	public IssueField getIssueField() {
		return issueField;
	}

}
