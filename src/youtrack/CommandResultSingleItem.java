package youtrack;

/**
 * Created by Egor.Malyshev on 25.12.2014.
 */
public class CommandResultSingleItem<R extends BaseItem> extends CommandResultBase<R> {

    public CommandResultSingleItem(YouTrack youTrack, int responseCode, R result) {
        super(youTrack, responseCode);
        this.result = result;
        if (result != null) this.result.setYouTrack(youTrack);
    }
}
