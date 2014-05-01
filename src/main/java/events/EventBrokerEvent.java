package events;

public class EventBrokerEvent {
    private final String type;

    public EventBrokerEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "EventBrokerEvent{" +
                "type='" + type + '\'' +
                '}';
    }
}
