package youtrack;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project extends BaseItem<YouTrack> {
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "shortName")
    private String shortName;
    Project() {
    }
    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
    public String getName() {
        return name;
    }
    public String getId() {
        return shortName;
    }
}