package youtrack;

import javax.xml.bind.annotation.*;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseItem {
    @XmlTransient
    protected YouTrack youTrack;
    @XmlValue
    protected String body;
    @XmlTransient
    protected boolean wrapper = false;
    @SuppressWarnings("UnusedDeclaration")
    @XmlAttribute(name = "id")
    private String id;

    BaseItem() {
    }

    YouTrack getYouTrack() {
        return youTrack;
    }

    void setYouTrack(YouTrack youTrack) {
        this.youTrack = youTrack;
    }

    public String getId() {
        return id;
    }
}