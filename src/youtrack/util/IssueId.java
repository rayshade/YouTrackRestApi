package youtrack.util;

/**
 * Created by egor.malyshev on 15.04.2014.
 */
public class IssueId {

    public final String projectId;
    public final String issueId;
    public final String fqId;

    public IssueId(String id) {
        this.projectId = id.substring(0, id.indexOf("-"));
        this.issueId = id.substring(projectId.length());
        this.fqId = id;
    }
}