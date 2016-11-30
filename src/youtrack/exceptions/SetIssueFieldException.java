package youtrack.exceptions;
import youtrack.Issue;
import youtrack.issues.fields.BaseIssueField;
/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class SetIssueFieldException extends Exception {
    private final Issue issue;
    private final BaseIssueField issueField;
    private final String value;
    public SetIssueFieldException(Issue issue, BaseIssueField issueField, String value) {
        super("Cannot set value of " + issueField.getName() + " in " + issue.getId() + " to " + value);
        this.issue = issue;
        this.issueField = issueField;
        this.value = value;
    }
    public Issue getIssue() {
        return issue;
    }
    public String getValue() {
        return value;
    }
    public BaseIssueField getIssueField() {
        return issueField;
    }
}
