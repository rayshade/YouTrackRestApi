package youtrack.commands;

import org.apache.commons.httpclient.HttpMethodBase;
import org.xml.sax.SAXException;
import youtrack.*;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;
import youtrack.issue.fields.*;
import youtrack.issue.fields.values.AttachmentFieldValue;
import youtrack.issue.fields.values.LinkFieldValue;
import youtrack.issue.fields.values.MultiUserFieldValue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public abstract class Command<R> {

	HttpMethodBase method;

	public abstract boolean usesAuthorization();

	public abstract R getResult() throws CommandExecutionException;

	/**
	 * Helper method to deserealize XML to objects. Used to interpret XML response received from YouTrack.
	 *
	 * @param xmlString Raw XML code.
	 * @return Instance of an object.
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws javax.xml.bind.JAXBException
	 * @throws org.xml.sax.SAXException
	 * @throws IOException
	 */
	Object objectFromXml(String xmlString) throws ParserConfigurationException, JAXBException, SAXException, IOException, XMLStreamException {

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader = xmlInputFactory.createXMLStreamReader(new StringReader(xmlString));

		streamReader = new HackedReader(streamReader);

		JAXBContext jaxbContext = JAXBContext.newInstance(Issue.class, IssueField.class, CustomFieldValue.class, AttachmentField.class,
				LinkField.class, MultiUserField.class, SingleField.class, MultiUserFieldValue.class, AttachmentFieldValue.class,
				LinkFieldValue.class, IssueCompactList.class, youtrack.commands.results.Error.class, IssueProjectList.class,
				Project.class, ProjectList.class, CommentList.class, LinkList.class, IssueLink.class, IssueAttachment.class, AttachmentList.class,
				IssueTag.class, TagList.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		return jaxbUnmarshaller.unmarshal(streamReader);
	}

	public abstract HttpMethodBase commandMethod(String baseHost) throws IOException, NoSuchIssueFieldException, CommandExecutionException;

	/**
	 * Class to work around the JAXB name handling.
	 * Forces upper case on the first letter of xsi:type attribute.
	 */
	private class HackedReader extends StreamReaderDelegate {

		public HackedReader(XMLStreamReader xmlStreamReader) {
			super(xmlStreamReader);
		}

		@Override
		public String getAttributeValue(int index) {
			String attributeValue = super.getAttributeValue(index);
			if (getAttributeLocalName(index).equals("type"))
				return attributeValue.substring(0, 1).toLowerCase() + attributeValue.substring(1);
			else return super.getAttributeValue(index);
		}

	}

}