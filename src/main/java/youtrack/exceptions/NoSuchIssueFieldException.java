package youtrack.exceptions;
import youtrack.Issue;
/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class NoSuchIssueFieldException extends Exception {
    private final Issue issue;
    private final String fieldName;
    public NoSuchIssueFieldException(Issue issue, String fieldName) {
        super("Issue " + issue.getId() + " has no field: " + fieldName);
        this.issue = issue;
        this.fieldName = fieldName;
    }
    public Issue getIssue() {
        return issue;
    }
    public String getFieldName() {
        return fieldName;
    }
}
