package youtrack;

import javax.xml.bind.annotation.*;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseItem<O extends BaseItem> {
    @XmlTransient
    protected YouTrack youTrack;
    @XmlValue
    protected String body;
    @XmlTransient
    protected boolean wrapper = false;
    @XmlTransient
    protected O owner;
    @SuppressWarnings("UnusedDeclaration")
    @XmlAttribute(name = "id")
    protected String id;

    BaseItem() {
    }

    public O getOwner() {
        return owner;
    }

    public void setOwner(O owner) {
        this.owner = owner;
        youTrack = owner.getYouTrack();
    }

    public YouTrack getYouTrack() {
        return youTrack;
    }

    public String getId() {
        return id;
    }

    public BaseItem createSnapshot() {
        return this;
    }
}