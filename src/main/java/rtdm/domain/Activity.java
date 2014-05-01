package rtdm.domain;

import org.joda.time.DateTime;

public class Activity extends Entity {

    public enum Event {
        NEW_RATING, CARD_CREATED, CARD_UPDATED
    }

    private String dashboardKey;

    private Event event;

    private DateTime timestamp;

    private Card card;

    public String getDashboardKey() {
        return dashboardKey;
    }

    public Event getEvent() {
        return event;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public Card getCard() {
        return card;
    }

    public Activity setDashboardKey(final String dashboardKey) {
        this.dashboardKey = dashboardKey;
        return this;
    }

    public Activity setEvent(final Event event) {
        this.event = event;
        return this;
    }

    public Activity setTimestamp(final DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Activity setCard(final Card card) {
        this.card = card;
        return this;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "event=" + dashboardKey +
                ", event=" + event +
                ", timestamp=" + timestamp +
                ", card=" + card +
                '}';
    }
}
