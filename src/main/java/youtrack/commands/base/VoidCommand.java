package youtrack.commands.base;
import youtrack.BaseItem;

import javax.annotation.Nonnull;

/**
 * Created by Egor.Malyshev on 25.03.2015.
 */
public abstract class VoidCommand<O extends BaseItem, R> extends Command<O, R> {
    public VoidCommand(@Nonnull O owner) {
        super(owner);
    }
}
