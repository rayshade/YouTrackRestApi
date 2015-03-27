package youtrack;

import youtrack.commands.*;

import javax.xml.bind.annotation.*;
import java.util.function.Supplier;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project extends BaseItem<YouTrack> {
    @XmlTransient
    private final ThreadLocal<CommandBasedList<Project, Issue>> issues;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "shortName")
    private String shortName;

    Project() {
        final Project thiz = this;
        issues = ThreadLocal.withInitial(new Supplier<CommandBasedList<Project, Issue>>() {
            @Override
            public CommandBasedList<Project, Issue> get() {
                return new CommandBasedList<Project, Issue>(thiz,
                        new AddIssue(thiz), new RemoveIssue(thiz), new GetIssues(thiz), new QueryIssues(thiz), new GetIssue(thiz));
            }
        });
    }

    public CommandBasedList<Project, Issue> issues() {
        return issues.get();
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