package youtrack;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import youtrack.exceptions.CommandExecutionException;
import youtrack.issues.fields.*;
import youtrack.issues.fields.values.*;

import javax.xml.bind.JAXBContext;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public abstract class Command<O extends BaseItem, R> {
    protected final O owner;
    protected final YouTrack youTrack;
    CloseableHttpResponse response;
    Map<String, String> parameters = new HashMap<String, String>();
    private static final Pattern typeNamePattern = Pattern.compile("xsi:type=\"([a-zA-Z]+?)\"");

    private static final List<String> allowedTypeNames = Arrays.asList("attachmentField",
            "baseIssueField",
            "customField",
            "customFieldValue",
            "issueField",
            "issueListField",
            "linkField",
            "multiUserField",
            "singleField",
            "sprintField"
    );

    Command(final @NotNull O owner) {
        this.owner = owner;
        youTrack = owner.getYouTrack();
    }

    @Override
    public String toString() {
        return "Command " + this.getClass().getSimpleName() + "{" +
                "owner=" + owner +
                '}';
    }

    @NotNull
    O getOwner() {
        return owner;
    }

    boolean usesAuthorization() {
        return true;
    }

    @NotNull
    CommandResult<R> getResult() {
        return new CommandResult<R>(this);
    }

    /**
     * Helper method to deserealize XML to objects. Used to interpret XML response received from YouTrack.
     *
     * @param xmlString Raw XML code.
     * @return Instance of an object.
     */
    <T> T objectFromXml(final @NotNull String xmlString) throws Exception {
        //noinspection unchecked
        return (T) JAXBContext.newInstance(AttachmentField.class,
                BaseIssueField.class,
                CustomField.class,
                CustomFieldValue.class,
                IssueField.class,
                IssueListField.class,
                LinkField.class,
                MultiUserField.class,
                SingleField.class, AttachmentFieldValue.class,
                BaseIssueFieldValue.class,
                IssueFieldValue.class,
                LinkFieldValue.class,
                MultiUserFieldValue.class, AttachmentList.class,
                CommentList.class,
                Issue.class,
                IssueAttachment.class,
                IssueComment.class,
                IssueCompactList.class,
                IssueLink.class,
                IssueProjectList.class,
                IssueTag.class,
                BaseItemList.class,
                SprintField.class,
                LinkList.class,
                ProjectList.class,
                TagList.class,
                Error.class,
                LoginResult.class
        ).createUnmarshaller().unmarshal(new StringReader(cleanupTypes(xmlString)));
    }

    private static String cleanupTypes(String xmlString) {
        int last = 0;
        final Matcher matcher = typeNamePattern.matcher(xmlString);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            sb.append(xmlString.substring(last, matcher.start()));
            final String typeName = matcher.group(1);
            final String cleanTypeName = typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
            sb.append("xsi:type=\"").append(!cleanTypeName.contains("Field") ||
                    allowedTypeNames.contains(cleanTypeName) ? cleanTypeName : "singleField").append("\"");
            last = matcher.end();
        }
        sb.append(xmlString.substring(last));
        return sb.toString();
    }

    abstract HttpRequestBase createMethod() throws IOException, CommandExecutionException;

    void setParameter(final @NotNull String name, final @Nullable String value) {
        parameters.put(name, value);
    }

    void removeParameter(final @NotNull String name) {
        parameters.remove(name);
    }

    void run(CloseableHttpClient httpClient, Header auth) throws IOException, CommandExecutionException {
        final HttpRequestBase method = createMethod();
        if (usesAuthorization()) method.addHeader(auth);
        response = httpClient.execute(method);
    }

    void close() throws IOException {
        if (response != null) response.close();
    }

    @NotNull
    private String encodeParam(@NotNull String param) {
        try {
            return URLEncoder.encode(param, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException ignored) {

        }
        return param;
    }

    @NotNull
    String parametersAsQuery() {
        final StringBuilder sb = new StringBuilder();
        for (final String key : parameters.keySet()) {
            sb.append(key).append("=").append(encodeParam(parameters.get(key))).append("&");
        }
        return sb.toString();
    }
}