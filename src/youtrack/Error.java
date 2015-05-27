package youtrack;
import javax.xml.bind.annotation.*;
/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class Error {
    @XmlValue
    private String message;
    @XmlTransient
    private int code;
    public Error() {
    }
    public String getMessage() {
        return message;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
}
