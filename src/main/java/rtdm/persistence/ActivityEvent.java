package rtdm.persistence;

import rtdm.domain.Activity;
import events.EventBrokerEvent;

public final class ActivityEvent extends EventBrokerEvent {
    private final Activity activity;

    public ActivityEvent(Activity activity) {
        super("activity");

        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public String toString() {
        return "ActivityEvent{" +
                "activity=" + activity +
                '}';
    }
}
