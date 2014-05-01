package events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.factory.AutoStartable;
import restx.factory.Component;

import java.io.IOException;

@Component
public class RabbitMQEventBroker implements EventBroker, AutoStartable {
    private final static Logger logger = LoggerFactory.getLogger(RabbitMQEventBroker.class);

    private final ConnectionFactory factory;
    private final ObjectMapper objectMapper;
    private Connection connection;
    private Channel channel;

    public RabbitMQEventBroker(ConnectionFactory factory, ObjectMapper objectMapper) {
        this.factory = factory;
        this.objectMapper = objectMapper;
    }

    @Override
    public synchronized void start() {
        // Start the broker only if it has not been started before.
		if (connection == null) {
			logger.info("starting event broker {}", this);
			try {
				connection = factory.newConnection();
				channel = connection.createChannel();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

    @Override
    public synchronized void close() {
        logger.info("closing event broker {}", this);
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (IOException e) {
            logger.warn("error when closing channel " + channel, e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (IOException e) {
            logger.warn("error when closing channel " + channel, e);
        }
        channel = null;
        connection = null;
    }

    @Override
    public void broadcast(final String routingKey, final EventBrokerEvent event) {
        logger.info("broadcasting {} on {}", event, routingKey);
        try {
            byte[] msg = objectMapper.writeValueAsString(event).getBytes(Charsets.UTF_8);
            getChannel().basicPublish("amq.topic", routingKey, null, msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Channel getChannel() {
        ensureStarted();
        return channel;
    }

    private synchronized void ensureStarted() {
        start(); // start is starting only if not started
    }
}
