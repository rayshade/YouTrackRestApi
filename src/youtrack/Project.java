package youtrack;

import youtrack.commands.AddIssue;

import javax.xml.bind.annotation.*;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project extends BaseItem {
    @XmlTransient
    public final CommandBasedList<Project, Issue> issues;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "shortName")
    private String shortName;

    Project() {
        issues = new CommandBasedList<Project, Issue>(this,
                new AddIssue(this), null, null, null, null);
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