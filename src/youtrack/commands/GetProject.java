package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Project;
import youtrack.ProjectList;
import youtrack.YouTrack;
import youtrack.commands.base.SingleItemCommand;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

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
    public Project getResult() throws CommandExecutionException {
        try {
            final ProjectList projectList = (ProjectList) objectFromXml(method.getResponseBodyAsString());
            final List<Project> items = projectList.getItems();
            if (items != null) {
                for (final Project project : items) {
                    if (itemId.equals(project.getId())) return project;
                }
            }
            throw new Exception("Project " + itemId + " not found.");
        } catch (Exception e) {
            throw new CommandExecutionException(this, e);
        }
    }

    @Override
    public HttpMethodBase commandMethod(@NotNull String baseHost) throws IOException, NoSuchIssueFieldException, CommandExecutionException {
        method = new GetMethod(baseHost + "project/all");
        return method;
    }
}
