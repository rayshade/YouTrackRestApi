package youtrack.commands;
import com.sun.istack.internal.NotNull;
import org.apache.commons.httpclient.methods.GetMethod;
import youtrack.Issue;
import youtrack.IssueLink;
import youtrack.LinkList;
import youtrack.commands.base.ListCommand;
import youtrack.util.Service;

import java.util.Collections;
import java.util.List;
/**
 * Created by egor.malyshev on 02.04.2014.
 */
public class GetIssueLinks extends ListCommand<Issue, IssueLink> {
    public GetIssueLinks(@NotNull Issue owner) {
        super(owner);
    }
    @NotNull
    @Override
    public List<IssueLink> getResult() throws Exception {
        final LinkList linkList = (LinkList) objectFromXml(Service.readStream(method.getResponseBodyAsStream()));
        final List<IssueLink> list = linkList.getItems();
        if(list == null) return Collections.emptyList();
        for(IssueLink link : list) {
            link.setOwner(owner);
        }
        return list;
    }
    @Override
    public void createCommandMethod() {
        method = new GetMethod(owner.getYouTrack().getHostAddress() + "issue/" + owner.getId() + "/link");
    }
}