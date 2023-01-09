package vd.martynov.dinoshelterbot.configuration;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import vd.martynov.dinoshelterbot.commands.Command;

import java.util.Map;

/**
 * Основная конфигурация бота
 */
@Configuration
public class BotConfiguration {

    // Авторизационный токен Discord-бота
    @Value("${token}")
    private String token;

    /**
     * Создание объекта {@link discord4j.core.DiscordClient} для дальнейшей регистрации событий
     * @param commands
     * @return
     */
    @Bean
    GatewayDiscordClient gatewayDiscordClient(Map<String, Command> commands) {
        GatewayDiscordClient client = DiscordClientBuilder.create(token).build().login().block();

        Assert.notNull(client, "Discord client cannot be null");

        client.on(MessageCreateEvent.class)
                .flatMap(event -> Mono.just(event.getMessage().getContent())
                        .flatMap(content -> routeCommands(commands, content, event)))
                .subscribe();
        return client;
    }

    /**
     * Обработка команд
     * @param commands
     * @param content
     * @param event
     * @return
     */
    private Mono<Void> routeCommands(Map<String, Command> commands, String content, MessageCreateEvent event) {
        // Если предшествующее сообщение написано ботом, игнорировать
        if (event.getMessage().getAuthor().orElseThrow().isBot()) {
            return Mono.empty();
        }

        // Разделение в зависимости от текста сообщения
        Command command;
        String message = content.toLowerCase();
        if (message.contains("привет") && message.contains("шелтер")) {
            command = commands.get("greeting");
        } else if (message.startsWith("суд") || message.startsWith("туд")) {
            command = commands.get("sutu");
        } else if (message.contains("шеллвентарь")) {
            command = commands.get("inventory");
        } else {
            return Mono.empty();
        }
        return command.execute(event);
    }
}
