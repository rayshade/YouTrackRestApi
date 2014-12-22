package youtrack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Item {
    @XmlTransient
    protected YouTrack youTrack;
    @XmlValue
    protected String body;
    @XmlTransient
    protected boolean wrapper = false;

    Item() {
    }

    YouTrack getYouTrack() {
        return youTrack;
    }

    void setYouTrack(YouTrack youTrack) {
        this.youTrack = youTrack;
    }

    abstract String getId();
}
