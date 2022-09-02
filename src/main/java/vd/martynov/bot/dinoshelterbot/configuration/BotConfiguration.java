package vd.martynov.bot.dinoshelterbot.configuration;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import vd.martynov.bot.dinoshelterbot.listeners.EventListener;

import java.util.List;

@Configuration
public class BotConfiguration {

    @Value("${token}")
    private String token;

    @Bean
    <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> listeners) {
        GatewayDiscordClient client = DiscordClientBuilder.create(token).build().login().block();

        Assert.notNull(client, "Discord client cannot be null");
        listeners.forEach(listener -> client.on(listener.getEventType())
                .flatMap(listener::execute).onErrorResume(listener::handleError)
                .subscribe());
        return client;
    }
}
