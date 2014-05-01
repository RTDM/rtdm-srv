package events;

/**
 * Event Broker is responsible for asynchronously sending events that client listeners can listen to.
 */
public interface EventBroker {
    void broadcast(String routingKey, final EventBrokerEvent event);
    void close();
    void start();
}
