package impl;

import youtrack.*;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.CommandNotAvailableException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;
import java.util.List;

/**
 * Created by egor.malyshev on 07.04.2014.
 */
public class Sample {

	public static void main(String[] args) throws IOException, AuthenticationErrorException, NoSuchIssueFieldException,
			CommandNotAvailableException, CommandExecutionException {

		//Access a YouTrack instance by its REST URL.

		YouTrack youTrack = YouTrack.getInstance("http://youtrack.jetbrains.com/rest/");


		//Try to log in using some credentials.

		youTrack.login("megor", "H8gpr09,");

		//Get a list of all projects.
		List<Project> projectList = youTrack.projects();

		System.out.println("Total projects: " + projectList.size());

		//Getting a specific project. I'm, using DOC so as not to introduce too many disturbance :)

		Project project = youTrack.project("DOC");

		//Output full name.
		System.out.println("Project " + project.getName());

		//Now get some issue by its id:
		Issue issue = project.issues.item(0);

		//Let's out some info:

		System.out.println("Assignee: " + issue.getAssignee().getFullName());
		System.out.println("Summary: " + issue.getSummary());
//		System.out.println("Description: " + issue.getDescription());
		System.out.println("Votes: " + issue.getVotes());

		for (IssueLink issueLink: issue.links.list()) {
			System.out.println(issueLink.toString());
		}

		//Now see if anyone commented
/*


		System.out.println(issue.comments.list().size() + " total comments");

		//Okay, we can comment on it, too:
		System.out.println("Commenting...");
		IssueComment newComment = issue.comments.add(IssueComment.createComment("Hey people, I am commenting via API!"));

		//And see if we did OK. This is our comment:
		System.out.println(newComment.toString());

		//And new comment count as well:
		System.out.println(issue.comments.list().size() + " total comments");

		//Let's do same thing for tags. Notice that we're caching the list for output.
		issue.tags.add(IssueTag.createTag("new tag"));
		List<IssueTag> issueTags = issue.tags.list();

		System.out.println("Total tags: " + issueTags.size());

		for (IssueTag tag : issueTags) {
			System.out.println(tag.toString());
		}

		//Suppose we don't like one of the tags and want to remove them.

		IssueTag issueTag = issueTags.get(0);

		System.out.println("Removing tag " + issueTag.toString());

		issue.tags.remove(issueTag);

		//Now re-read issue state and show the number of tags.
		System.out.println("Total tags: " + issue.tags.list().size());

		//And finally, let's see attachments.

		List<IssueAttachment> issueAttachments = issue.attachments.list();
		for (IssueAttachment issueAttachment : issueAttachments) {
			System.out.println(issueAttachment.toString());
		}

		//And save one locally
		issueAttachments.get(0).saveTo("C:");
*/

	}


}
