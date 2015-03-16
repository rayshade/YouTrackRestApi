package youtrack;

import java.util.List;

/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public class CommandResultItemList<R extends BaseItem> extends CommandResultBase<List<R>> {
    public CommandResultItemList(YouTrack youTrack, int responseCode, List<R> result) {
        super(youTrack, responseCode);
        this.result = result;
        if (result != null)
            for (R item : this.result) {
                item.setYouTrack(youTrack);
            }
    }
}