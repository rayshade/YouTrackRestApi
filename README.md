YouTrackRestApi
===============

A set of Java classes wrapped around YouTrack REST API that provides convenient way to interact with YouTrack from your Java applications. This simple app explains most of the API functionality and gives a good starting point if you decide to try it yourself.

    package impl;

    import youtrack.*;

    import java.util.List;

    public class Sample {

    public static void main(String[] args) throws Exception {

        //Access a YouTrack instance by its REST URL.

        final YouTrack youTrack = YouTrack.getInstance("http://youtrack.jetbrains.com/rest/");

        //Try to log in using some credentials.
        youTrack.login("megor", "password");

        //Get a list of all projects.
        final List<Project> projectList = youTrack.projects.list();

        System.out.println("Total projects: " + projectList.size());

        //Getting a specific project by its ID.
        final Project project = youTrack.projects.item("DOC");

        System.out.println("Project " + project.getName() + " total issues: " + project.issue.list().size());

        //Get all issues from project. This is, however, not really useful...
        //final List<Issue> issues = project.issues.list();
        //...So instead we'll query issue that match specific criteria.

        final List<Issue> issues = project.issue.query("reported by: #me #Unresolved");

        for (final Issue issue : issues) {
            System.out.println(issue.toString());
        }

        //Now get some issue by its id:
        final Issue issue = project.issues.item("DOC-3200");


        //When you're working with a live issue instance, it always performs actual operations like
        //reading or writing, which isn't always quite OK, so we're creating a snapshot, a copy of issue that
        //doesn't do that. It's useful when you only need to output issue fields like here.

        final Issue snapShot = issue.createSnapshot();

        System.out.println("Assignee: " + snapShot.getAssignee().getFullName());
        System.out.println("Summary: " + snapShot.getSummary());
        System.out.println("Votes: " + snapShot.getVotes());

        //Now we can have a look at issue attachments.
        for (IssueAttachment attachment : issue.attachments.list()) {
            System.out.println(attachment.toString());
        }

        //And also comments
        System.out.println(issue.comments.list().size() + " total comments.");

        //We can even leave a message.

        final CommandResultSingleItem<IssueComment> newComment =
                issue.comments.add(IssueComment.createComment("Hey people, I am commenting via API!"));

        //Same works for tags
        final List<IssueTag> issueTags = issue.tags.list();
        for (IssueTag tag : issueTags) {
            System.out.println(tag.toString());
        }

        //And attachments
        final List<IssueAttachment> issueAttachments = issue.attachments.list();
        for (IssueAttachment issueAttachment : issueAttachments) {
            System.out.println(issueAttachment.toString());

            //Saving them locally is easy, too.
            issueAttachment.saveTo("C:");
        }
    }
    }
