package youtrack;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;
import youtrack.exceptions.SetIssueFieldException;
import youtrack.issues.fields.BaseIssueField;
import youtrack.issues.fields.SingleField;
import youtrack.issues.fields.values.BaseIssueFieldValue;
import youtrack.issues.fields.values.IssueFieldValue;
import youtrack.issues.fields.values.MultiUserFieldValue;

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
@SuppressWarnings({"unused", "SameParameterValue", "WeakerAccess"})
@XmlRootElement(name = "issue")
@XmlAccessorType(XmlAccessType.FIELD)
public class Issue extends BaseItem<YouTrack> {
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
    @XmlTransient
    private boolean wikify;

    Issue() {
        final Issue thiz = this;
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

    public HashMap<String, BaseIssueField> getFields() {
        return new HashMap<String, BaseIssueField>(fields);
    }

    public Issue createSnapshot() {
        return new Issue(fields);
    }

    void setFieldByName(@NotNull String fieldName, @Nullable String value) throws SetIssueFieldException, IOException, NoSuchIssueFieldException, CommandExecutionException {
        if (fields.containsKey(fieldName)) {
            final ModifyIssueField modifyCommand = new ModifyIssueField(this);
            modifyCommand.setParameter("field", fieldName);
            modifyCommand.setParameter("value", value);
            final CommandResult<String> result = youTrack.execute(modifyCommand);
            if (!result.success()) {
                throw new SetIssueFieldException(this, fields.get(fieldName), value);
            }
            updateSelf();
        } else throw new NoSuchIssueFieldException(this, fieldName);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    <V extends BaseIssueFieldValue> V getFieldByName(@NotNull String fieldName) throws IOException, CommandExecutionException {
        if (fields.containsKey(fieldName)) {
            if (!wrapper) updateSelf();
            return (V) fields.get(fieldName).getValue();
        } else return null;
    }

    @SuppressWarnings("UnusedParameters")
    private void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        fields = new HashMap<String, BaseIssueField>();
        for (BaseIssueField issueField : fieldArray) {
            fields.put(issueField.getName(), issueField);
        }
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + getId() + '\'' +
                '}';
    }

    public boolean isResolved() {
        return fields.containsKey("resolved");
    }

    public String getState() throws IOException, CommandExecutionException {
        final BaseIssueFieldValue state = getFieldByName("State");
        return state == null ? null : state.getValue();
    }

    public void setState(String state) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        setFieldByName("State", state);
    }

    public boolean isWikify() {
        return wikify;
    }

    public void setWikify(boolean wikify) {
        this.wikify = wikify;
    }

    public String getDescription() throws IOException, CommandExecutionException {
        final BaseIssueFieldValue description = getFieldByName("description");
        return description == null ? null : description.getValue();
    }

    public void setDescription(String description) throws IOException, SetIssueFieldException, CommandExecutionException {
        final ModifyIssue command = new ModifyIssue(this);
        command.setParameter("description", description);
        final CommandResult<String> result = youTrack.execute(command);
        if (result.success()) {
            updateSelf();
        } else throw new SetIssueFieldException(this, fields.get("summary"), description);
    }

    public String getSummary() throws IOException, CommandExecutionException {
        BaseIssueFieldValue summary = getFieldByName("summary");
        return summary == null ? null : summary.getValue();
    }

    public void setSummary(String summary) throws IOException, SetIssueFieldException, CommandExecutionException {
        final ModifyIssue command = new ModifyIssue(this);
        command.setParameter("summary", summary);
        final CommandResult<String> result = youTrack.execute(command);
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

    public String getType() throws IOException, CommandExecutionException {
        BaseIssueFieldValue type = getFieldByName("Type");
        return type == null ? null : type.getValue();
    }

    public void setType(String type) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        setFieldByName("Type", type);
    }

    public String getPriority() throws IOException, CommandExecutionException {
        BaseIssueFieldValue priority = getFieldByName("Priority");
        return priority == null ? null : priority.getValue();
    }

    public void setPriority(String priority) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        setFieldByName("Priority", priority);
    }

    public MultiUserFieldValue getAssignee() throws IOException, CommandExecutionException {
        return (MultiUserFieldValue) getFieldByName("Assignee");
    }

    public void setAssignee(String assignee) throws IOException, SetIssueFieldException, NoSuchIssueFieldException, CommandExecutionException {
        setFieldByName("Assignee", assignee);
    }

    public String getReporter() throws IOException, CommandExecutionException {
        BaseIssueFieldValue reporterName = getFieldByName("reporterName");
        return reporterName == null ? null : reporterName.getValue();
    }

    public void vote() throws CommandExecutionException, IOException {
        youTrack.execute(new ChangeIssueVotes(this, true));
    }

    public void unVote() throws CommandExecutionException, IOException {
        youTrack.execute(new ChangeIssueVotes(this, false));
    }

    private void updateSelf() throws CommandExecutionException, IOException {
        final GetIssue command = new GetIssue(owner);
        if (wikify) command.setParameter("wikifyDescription", String.valueOf(true));
        command.setItemId(getId());
        final Issue issue = youTrack.execute(command).getResult();
        if (issue != null) {
            this.fields.clear();
            this.fields.putAll(issue.fields);
        }
    }

    public String getProjectId() {
        return getId().substring(0, getId().indexOf("-"));
    }
}