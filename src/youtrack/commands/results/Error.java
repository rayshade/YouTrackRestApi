package youtrack.commands.results;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class Error {

	@XmlValue
	private String errorMessage;

	public Error() {
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}