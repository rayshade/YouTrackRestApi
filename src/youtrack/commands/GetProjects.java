package youtrack.commands;
import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Project;
import youtrack.YouTrack;
import youtrack.commands.base.ListCommand;
/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetProjects extends ListCommand<YouTrack, Project> {
    public GetProjects(@NotNull YouTrack owner) {
        super(owner);
    }
/*    @NotNull
    @Override
    public List<Project> getResult() {
        final ProjectList projectList = (ProjectList) objectFromXml(Service.readStream(method.getResponseBodyAsStream()));
        final List<Project> items = projectList.getItems();
        if(items == null) return Collections.emptyList();
        for(Project project : items) {
            project.setOwner(owner);
        }
        return items;
    }*/
    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "project/all");
    }
}