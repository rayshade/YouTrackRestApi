package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Project;
import youtrack.ProjectList;
import youtrack.YouTrack;
import youtrack.commands.base.SingleItemCommand;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;
import youtrack.util.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Egor.Malyshev on 26.12.2014.
 */
public class GetProject extends SingleItemCommand<YouTrack, Project> {
    public GetProject(@NotNull YouTrack owner) {
        super(owner);
    }

    @Override
    public Project getResult() throws Exception {
        final ProjectList projectList = (ProjectList) objectFromXml(Service.readStream(method.getResponseBodyAsStream()));
        final List<Project> items = projectList.getItems();
        if (items != null) {
            for (final Project project : items) {
                if (itemId.equals(project.getId())) return project;
            }
        }
        throw new Exception("Project " + itemId + " not found.");
    }

    @Override
    public void createCommandMethod() throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "project/all");
    }
}
