package vd.martynov.dinoshelterbot.listeners.message.create;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import vd.martynov.dinoshelterbot.listeners.EventListener;
import vd.martynov.dinoshelterbot.listeners.message.MessageListener;
import vd.martynov.dinoshelterbot.persist.service.InventoryService;

@Slf4j
@Service
public class MessageCreateListener extends MessageListener implements EventListener<MessageCreateEvent> {

    @Autowired
    private InventoryService inventoryService;

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        User author = event.getMessage().getAuthor().orElse(null);
        if (author == null || author.isBot()) {
            return Mono.empty();
        }
        String message = event.getMessage().getContent().toLowerCase();
        log.info("Processing message: '{}'", message);
        if (message.contains("привет") && message.contains("шелтер")) {
            return processGreet(event.getMessage());
        } else if (message.startsWith("суд") || message.startsWith("туд")) {
            return processSuTu(event.getMessage());
        } else if (message.equals("шеллвентарь")) {
            return processInventory(author, inventoryService, event);
        } else return Mono.empty();
    }
}
