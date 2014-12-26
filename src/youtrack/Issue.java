package youtrack;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import youtrack.commands.*;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;
import youtrack.exceptions.SetIssueFieldException;
import youtrack.issue.fields.BaseIssueField;
import youtrack.issue.fields.SingleField;
import youtrack.issue.fields.values.BaseIssueFieldValue;
import youtrack.issue.fields.values.IssueFieldValue;
import youtrack.issue.fields.values.MultiUserFieldValue;
import youtrack.util.IssueId;

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
public class Issue extends BaseItem {
    @XmlTransient
    public final CommandBasedList<Issue, IssueComment> comments;
    @XmlTransient
    public final CommandBasedList<Issue, IssueAttachment> attachments;
    @XmlTransient
    public final CommandBasedList<Issue, IssueLink> links;
    @XmlTransient
    public final CommandBasedList<Issue, IssueTag> tags;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @XmlElement(name = "field")
    private List<BaseIssueField> fieldArray;
    @XmlTransient
    private HashMap<String, BaseIssueField> fields;

    Issue() {
        comments = new CommandBasedList<Issue, IssueComment>(this,
                new AddComment(this), new RemoveComment(this), new GetIssueComments(this), null, null);
        attachments = new CommandBasedList<Issue, IssueAttachment>(this, new AddAttachment(this),
                new RemoveAttachment(this), new GetIssueAttachments(this), null, null);
        links = new CommandBasedList<Issue, IssueLink>(this, new AddIssueLink(this), new RemoveIssueLink(this), new GetIssueLinks(this), null, null);
        tags = new CommandBasedList<Issue, IssueTag>(this, new AddIssueTag(this), new RemoveIssueTag(this), new GetIssueTags(this), null, null);
    }

    private Issue(String summary, String description) {
        wrapper = true;
        fieldArray = new ArrayList<BaseIssueField>();
        fieldArray.add(SingleField.createField("summary", IssueFieldValue.createValue(summary)));
        fieldArray.add(SingleField.createField("description", IssueFieldValue.createValue(description)));
        this.afterUnmarshal(null, null);
        tags = null;
        links = null;
        comments = null;
        attachments = null;
    }

    private Issue(Map<String, BaseIssueField> fields) {
        wrapper = true;
        tags = null;
        links = null;
        comments = null;
        attachments = null;
        this.fields = new HashMap<String, BaseIssueField>();
        this.fields.putAll(fields);
    }

    public static Issue createIssue(String summary, String description) {
        return new Issue(summary, description);
    }

    public Issue createSnapshot() {
        return new Issue(fields);
    }

    void setFieldByName(@NotNull String fieldName, @Nullable String value) throws SetIssueFieldException, IOException, NoSuchIssueFieldException, CommandExecutionException {
        if (fields.containsKey(fieldName)) {
            final ModifyIssueField modifyCommand = new ModifyIssueField(this);
            final Map<String, String> params = new HashMap<String, String>();
            params.put("field", fieldName);
            params.put("value", value);
            modifyCommand.setArguments(params);
            CommandResultData<String> result = youTrack.execute(modifyCommand);
            if (!result.success()) {
                throw new SetIssueFieldException(this, fields.get(fieldName), value);
            }
            updateSelf();
        } else throw new NoSuchIssueFieldException(this, fieldName);
    }

    @SuppressWarnings("unchecked")
    <V extends BaseIssueFieldValue> V getFieldByName(@NotNull String fieldName) throws NoSuchIssueFieldException, IOException, CommandExecutionException {
        if (fields.containsKey(fieldName)) {
            if (!wrapper) updateSelf();
            return (V) fields.get(fieldName).getValue();
        } else throw new NoSuchIssueFieldException(this, fieldName);
    }

    @SuppressWarnings("UnusedParameters")
    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        fields = new HashMap<String, BaseIssueField>();
        for (BaseIssueField issueField : fieldArray) {
            fields.put(issueField.getName(), issueField);
        }
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + getId() + '\'' +
                ", fieldArray=" + fieldArray +
                '}';
    }

    public boolean isResolved() {
        return fields.containsKey("resolved");
    }

    public String getState() throws NoSuchIssueFieldException, IOException, CommandExecutionException {
        return getFieldByName("State").getValue();
    }

    public void setState(String state) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        setFieldByName("State", state);
    }

    public String getDescription() throws NoSuchIssueFieldException, IOException, CommandExecutionException {
        return getFieldByName("description").getValue();
    }

    public void setDescription(String description) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        final ModifyIssue command = new ModifyIssue(this);
        final Map<String, String> params = new HashMap<String, String>();
        params.put("description", description);
        command.setArguments(params);
        final CommandResultData<String> result = youTrack.execute(command);
        if (result.success()) {
            updateSelf();
        } else throw new SetIssueFieldException(this, fields.get("summary"), description);
    }

    public String getSummary() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        return getFieldByName("summary").getValue();
    }

    public void setSummary(String summary) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        final ModifyIssue command = new ModifyIssue(this);
        final Map<String, String> params = new HashMap<String, String>();
        params.put("summary", summary);
        command.setArguments(params);
        final CommandResultData<String> result = youTrack.execute(command);
        if (result.success()) {
            updateSelf();
        } else throw new SetIssueFieldException(this, fields.get("summary"), summary);
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
        setFieldByName("Type", type);
    }

    public String getPriority() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        return getFieldByName("Priority").getValue();
    }

    public void setPriority(String priority) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        setFieldByName("Priority", priority);
    }

    public MultiUserFieldValue getAssignee() throws NoSuchIssueFieldException, IOException, CommandExecutionException {
        return (MultiUserFieldValue) getFieldByName("Assignee");
    }

    public void setAssignee(String assignee) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        setFieldByName("Assignee", assignee);
    }

    public String getReporter() throws NoSuchIssueFieldException, IOException, CommandExecutionException {
        return getFieldByName("reporterName").getValue();
    }

    public void vote() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        youTrack.execute(new ChangeIssueVotes(this, true));
    }

    public void unVote() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        youTrack.execute(new ChangeIssueVotes(this, false));
    }

    public String getProjectId() {
        return new IssueId(getId()).projectId;
    }

    private void updateSelf() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        final GetIssue command = new GetIssue(youTrack.projects.item(getProjectId()));
        command.setItemId(getId());
        final Issue issue = youTrack.execute(command).getResult();
        if (issue != null) {
            this.fields.clear();
            this.fields.putAll(issue.fields);
        }
    }
}