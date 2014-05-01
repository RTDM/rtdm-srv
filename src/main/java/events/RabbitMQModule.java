package events;

import com.google.common.base.Strings;
import com.rabbitmq.client.ConnectionFactory;
import restx.config.Settings;
import restx.config.SettingsKey;
import restx.factory.Module;
import restx.factory.Provides;

@Module
public class RabbitMQModule {
    @Settings
    public static interface RabbitMQSettings {
        @SettingsKey(key = "rabbitmq.host", defaultValue = "localhost")
        String host();
        @SettingsKey(key = "rabbitmq.username", defaultValue = "")
        String username();
        @SettingsKey(key = "rabbitmq.password", defaultValue = "")
        String password();
    }

    @Provides
    public ConnectionFactory connectionFactory(RabbitMQSettings settings) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(settings.host());
        if (!Strings.isNullOrEmpty(settings.username())) {
            factory.setUsername(settings.username());
            factory.setPassword(settings.password());
        }
        return factory;
    }
}
