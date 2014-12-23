package youtrack;

import youtrack.commands.*;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;
import youtrack.exceptions.SetIssueFieldException;
import youtrack.issue.fields.IssueField;
import youtrack.issue.fields.SingleField;
import youtrack.issue.fields.values.BaseIssueFieldValue;
import youtrack.issue.fields.values.IssueFieldValue;
import youtrack.issue.fields.values.MultiUserFieldValue;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Egor.Malyshev on 19.12.13.
 * Provides access to a single issue and its fields.
 */
@XmlRootElement(name = "issue")
@XmlAccessorType(XmlAccessType.FIELD)
public class Issue extends Item {

	/*
    * These lists provide live access to issue comments and so on.
	*
	*/

    @XmlTransient
    public final CommandBasedList<IssueComment> comments;
    @XmlTransient
    public final CommandBasedList<IssueAttachment> attachments;
    @XmlTransient
    public final CommandBasedList<IssueLink> links;
    @XmlTransient
    public final CommandBasedList<IssueTag> tags;
    @XmlAttribute(name = "id")
    private String id;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @XmlElement(name = "field")
    private List<IssueField> fieldArray;
    /*
    This is used to work around the issue with JAXB not being able to unmarshal a Map.
     */
    @XmlTransient
    private HashMap<String, IssueField> fields;

    Issue() {
        comments = new CommandBasedList<IssueComment>(this, AddComment.class, RemoveComment.class, GetIssueComments.class, null, null);
        attachments = new CommandBasedList<IssueAttachment>(this, AddAttachment.class, RemoveAttachment.class, GetIssueAttachments.class, null, null);
        links = new CommandBasedList<IssueLink>(this, AddIssueLink.class, RemoveIssueLink.class, GetIssueLinks.class, null, null);
        tags = new CommandBasedList<IssueTag>(this, AddIssueTag.class, RemoveIssueTag.class, GetIssueTags.class, null, null);
    }

    private Issue(String summary, String description) {
        wrapper = true;
        fieldArray = new ArrayList<IssueField>();
        fieldArray.add(SingleField.createField("summary", IssueFieldValue.createValue(summary)));
        fieldArray.add(SingleField.createField("description", IssueFieldValue.createValue(description)));
        this.afterUnmarshal(null, null);
        tags = null;
        links = null;
        comments = null;
        attachments = null;
    }

    private Issue(Map<String, IssueField> fields) {
        wrapper = true;
        tags = null;
        links = null;
        comments = null;
        attachments = null;
        this.fields = new HashMap<String, IssueField>();
        this.fields.putAll(fields);
    }

    public static Issue createIssue(String summary, String description) {
        return new Issue(summary, description);
    }

    public Issue createSnapshot() {
        return new Issue(fields);
    }

    void setFieldByName(String fieldName, BaseIssueFieldValue value) throws SetIssueFieldException, IOException, NoSuchIssueFieldException, CommandExecutionException {

        if (fields.containsKey(fieldName)) {

            CommandResult result = youTrack.execute(new ModifyIssueField(this, fields.get(fieldName), value));

            if (!result.success()) {

                throw new SetIssueFieldException(this, fields.get(fieldName), value);
            }

            fields.get(fieldName).setValue(value);
        } else throw new NoSuchIssueFieldException(this, fieldName);
    }

    BaseIssueFieldValue getFieldByName(String fieldName) throws NoSuchIssueFieldException, IOException, CommandExecutionException {

        if (fields.containsKey(fieldName)) {

            if (!wrapper) updateSelf();

            return fields.get(fieldName).getValue();

        } else throw new NoSuchIssueFieldException(this, fieldName);
    }

    @SuppressWarnings("UnusedDeclaration")
    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        fields = new HashMap<String, IssueField>();
        for (IssueField issueField : fieldArray) {
            fields.put(issueField.getName(), issueField);
        }
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + id + '\'' +
                ", fieldArray=" + fieldArray +
                '}';
    }

    public String getId() {
        return id;
    }

    public boolean isResolved() {
        return fields.containsKey("resolved");
    }

    public String getState() throws NoSuchIssueFieldException, IOException, CommandExecutionException {
        return getFieldByName("State").getValue();
    }

    public void setState(String state) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        setFieldByName("State", new IssueFieldValue(state));
    }

    public String getDescription() throws NoSuchIssueFieldException, IOException, CommandExecutionException {
        return getFieldByName("description").getValue();
    }

    public void setDescription(String description) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {

        CommandResult result = youTrack.execute(new ModifyIssue(this, null, description));

        if (result.success()) {

            fields.get("description").setValue(new IssueFieldValue(description));

        } else
            throw new SetIssueFieldException(this, fields.get("description"), IssueFieldValue.createValue(description));

    }

    public String getSummary() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        return getFieldByName("summary").getValue();
    }

    public void setSummary(String summary) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {

        CommandResult result = youTrack.execute(new ModifyIssue(this, summary, null));

        if (result.success()) {

            fields.get("summary").setValue(new IssueFieldValue(summary));

        } else throw new SetIssueFieldException(this, fields.get("summary"), IssueFieldValue.createValue(summary));
    }

    public int getVotes() {
        try {
            return Integer.parseInt(getFieldByName("votes").getValue());
        } catch (Exception e) {
            return 0;
        }
    }

    public String getType() throws NoSuchIssueFieldException, IOException, CommandExecutionException {
        return getFieldByName("Type").getValue();
    }

    public void setType(String type) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        setFieldByName("Type", new IssueFieldValue(type));
    }

    public String getPriority() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        return getFieldByName("Priority").getValue();
    }

    public void setPriority(String priority) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        setFieldByName("Priority", new IssueFieldValue(priority));
    }

    public MultiUserFieldValue getAssignee() throws NoSuchIssueFieldException, IOException, CommandExecutionException {
        return (MultiUserFieldValue) getFieldByName("Assignee");
    }

    public String getReporter() throws NoSuchIssueFieldException, IOException, CommandExecutionException {
        return getFieldByName("reporterName").getValue();

    }

    public void setAssignee(String assignee, String fullName) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        MultiUserFieldValue value = new MultiUserFieldValue(assignee);
        value.setFullName(fullName);
        setFieldByName("Assignee", value);
    }

    public void vote() throws IOException, NoSuchIssueFieldException, CommandExecutionException {

        youTrack.execute(new ChangeIssueVotes(this, true)).success();
    }

    public void unVote() throws IOException, NoSuchIssueFieldException, CommandExecutionException {

        youTrack.execute(new ChangeIssueVotes(this, false)).success();
    }

    private void updateSelf() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        Issue issue = youTrack.execute(new GetIssue(this.id)).getData();
        if (issue != null) {
            this.fields.clear();
            this.fields.putAll(issue.fields);
        }
    }
}