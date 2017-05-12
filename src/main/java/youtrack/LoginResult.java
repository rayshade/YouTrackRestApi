package youtrack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
@XmlRootElement(name = "login")
@XmlAccessorType(XmlAccessType.FIELD)
class LoginResult {
    public String getMessage() {
        return message;
    }

    @XmlValue
    private String message;

    LoginResult() {
    }
}
