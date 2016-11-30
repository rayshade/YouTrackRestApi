package youtrack;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import youtrack.exceptions.CommandExecutionException;
import youtrack.issues.fields.*;
import youtrack.issues.fields.values.*;

import javax.xml.bind.JAXBContext;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public abstract class Command<O extends BaseItem, R> {
    protected final O owner;
    CloseableHttpResponse response;

    protected final YouTrack youTrack;
    Map<String, String> parameters = new HashMap<String, String>();

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
    abstract CommandResult<R> getResult();

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
                Error.class).createUnmarshaller().unmarshal(new HackedReader(XMLInputFactory.newInstance().createXMLStreamReader(new StringReader(xmlString))));
    }

    abstract HttpRequestBase createMethod() throws IOException, CommandExecutionException;

    void setParameter(final @NotNull String name, final @Nullable String value) {
        parameters.put(name, value);
    }

    void removeParameter(final @NotNull String name) {
        if (parameters.containsKey(name)) parameters.remove(name);
    }

    void run(CloseableHttpClient httpClient, String authToken) throws IOException, CommandExecutionException {
        final HttpRequestBase method = createMethod();
        if (usesAuthorization()) method.addHeader("Cookie", authToken);
        response = httpClient.execute(method);
    }

    /**
     * Class to work around the JAXB name handling.
     * Forces upper case on the first letter of xsi:type attribute.
     */
    private class HackedReader extends StreamReaderDelegate {
        HackedReader(XMLStreamReader xmlStreamReader) {
            super(xmlStreamReader);
        }

        private final List<String> allowedNames = Arrays.asList("AttachmentField",
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

        @Override
        public String getAttributeValue(int index) {
            final String attributeValue = super.getAttributeValue(index);
            if (!"type".equals(getAttributeLocalName(index))) return super.getAttributeValue(index);
            final String valueToReturn = attributeValue.substring(0, 1).toLowerCase() + attributeValue.substring(1);
            return valueToReturn.contains("Field") ? allowedNames.contains(valueToReturn) ? valueToReturn : "singleField" : valueToReturn;
        }
    }

    void close() throws IOException {
        if (response != null) response.close();
    }

    @NotNull
    private String encodeParam(@NotNull String params) {
        try {
            return URLEncoder.encode(params, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException ignored) {

        }
        return params;
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