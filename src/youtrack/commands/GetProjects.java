package youtrack.commands;


import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Project;
import youtrack.ProjectList;
import youtrack.YouTrack;
import youtrack.commands.base.ListCommand;
import youtrack.exceptions.CommandExecutionException;

import java.util.Collections;
import java.util.List;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetProjects extends ListCommand<YouTrack, Project> {

    public GetProjects(@NotNull YouTrack owner) {
        super(owner);
    }

    @NotNull
    @Override
    public List<Project> getResult() throws Exception {
        final ProjectList projectList = (ProjectList) objectFromXml(method.getResponseBodyAsString());
        final List<Project> items = projectList.getItems();
        return items != null ? items : Collections.<Project>emptyList();
    }

    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "project/all");
    }
}