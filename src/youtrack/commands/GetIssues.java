package youtrack.commands;

import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueProjectList;
import youtrack.Project;
import youtrack.commands.base.ListCommand;
import youtrack.util.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
public class GetIssues extends ListCommand<Project, Issue> {

    public GetIssues(@NotNull Project owner) {
        super(owner);
    }

    @NotNull
    @Override
    public List<Issue> getResult() throws Exception {
        final IssueProjectList itemList = (IssueProjectList) objectFromXml(Service.readStream(method.getResponseBodyAsStream()));
        final List<Issue> list = itemList.getItems();
        if (list == null) return Collections.emptyList();
        for (final Issue issue : list) {
            issue.setOwner(owner);
        }
        return list;
    }

    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/byproject/" + getOwner().getId());
    }
}