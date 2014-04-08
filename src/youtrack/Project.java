package youtrack;

import youtrack.commands.*;

import javax.xml.bind.annotation.*;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project extends Item {
	@XmlTransient
	public final CommandBasedList<Issue> issues;
	@XmlAttribute(name = "name")
	private String name;
	@XmlAttribute(name = "shortName")
	private String id;

	Project() {
		issues = new CommandBasedList<Issue>(this, AddIssue.class, RemoveIssue.class, GetIssues.class, QueryIssues.class, GetIssue.class);
	}

	@Override
	public String toString() {
		return "Project{" +
				"name='" + name + '\'' +
				", id='" + id + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
}