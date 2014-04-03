package example;

import youtrack.Issue;
import youtrack.IssueComment;
import youtrack.Project;
import youtrack.YouTrack;

import java.util.List;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class Example {

	public static void main(String[] args) {

		YouTrack youTrack = YouTrack.getInstance("http://youtrack.jetbrains.com/rest/");

		if (youTrack.doLogin("login", "password")) {

			List<Project> projectList = youTrack.listProjects();

			System.out.println("Total projects: " + projectList.size());

			Project project = youTrack.project("DOC");

			if (project != null) {

				System.out.println("Project " + project.getName());

				Issue issue = project.issue("DOC-3261");

				if (issue != null) {
					System.out.println("Issue assignee: " + issue.getAssignee().getFullName());

					System.out.println(issue.comments.list().size() + " total comments");

					IssueComment newComment = issue.comments.add(IssueComment.createComment("Test comment from API!!"));

					System.out.println(issue.comments.list().size() + " total comments");

					System.out.println(newComment.toString());

				}
			}

		}
	}

}