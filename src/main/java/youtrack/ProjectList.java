package youtrack;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectList extends BaseItemList<Project> {
    @XmlElement(name = "project")
    private List<Project> projects;
    ProjectList() {
    }
    @Override
    public List<Project> getItems() {
        return projects;
    }
    @Override
    public String toString() {
        return "ProjectList{" +
                "projects=" + projects +
                '}';
    }
}