package youtrack;

import java.util.List;

/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public class CommandResultItemList<R extends BaseItem> extends CommandResultBase<List<R>> {
    public CommandResultItemList(YouTrack youTrack, int responseCode, List<R> result) {
        super(youTrack, responseCode);
        for (R item : result) {
            item.setYouTrack(youTrack);
            result.add(item);
        }
    }
}