package vd.martynov.dinoshelterbot.listeners.message;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import vd.martynov.dinoshelterbot.persist.entity.Inventory;
import vd.martynov.dinoshelterbot.persist.service.InventoryService;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Slf4j
public abstract class MessageListener {

    private final List<String> greetings = List.of(
            "https://c.tenor.com/W_u3Ncn-iiUAAAAC/отец-здарова.gif",
            "https://c.tenor.com/nFXaaZBGaRcAAAAC/hat-tip-greetings.gif",
            "https://c.tenor.com/pqqlX7Ha8PcAAAAC/hello-bob.gif",
            "https://c.tenor.com/rlWKA9-VamsAAAAC/despicable-me-gru.gif",
            "https://c.tenor.com/e02iqUN5EjgAAAAC/dumb-dumber.gif",
            "https://c.tenor.com/Y35j9akmzecAAAAC/merry-christmas-baby-funny-animals.gif"
            );

    private final Random random = new Random();

    public Mono<Void> processGreet(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(getRandomElement()))
                .then();
    }

    public Mono<Void> processSuTu(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(swapSuTu(message)))
                .then();
    }

    public Mono<Void> processInventory(User author, InventoryService inventoryService, MessageCreateEvent event) {
        log.info("{} id: {}", author.getUsername(), author.getId().asLong());
        long id = author.getId().asLong();
        EmbedCreateSpec.Builder responseBuilder = EmbedCreateSpec.builder();
        Inventory inventory = inventoryService.getInventory(id);
        if (inventory == null) {
            responseBuilder
                    .color(Color.CINNABAR)
                    .description("К сожалению, Ваш инвентарь еще пуст.");
        } else {
            responseBuilder
                    .color(Color.of(115, 138, 219))
                    .description("Содержимое инвентаря:");
            inventory.getItems().forEach(item ->
                    responseBuilder.addField(item.getName(), "x" + item.getQuantity(), true));

        }
        responseBuilder.timestamp(Instant.now());
        return Mono.just(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(responseBuilder.build()))
                .then();
    }

    private String getRandomElement() {
        return greetings.get(random.nextInt(greetings.size()));
    }

    private String swapSuTu(Message message) {
        String word = message.getContent();
        return (word.toLowerCase().startsWith("с") ?
                (Character.isLowerCase(word.charAt(0)) ? "т" : "Т") :
                (Character.isLowerCase(word.charAt(0)) ? "с" : "С"))
                + word.substring(1);
     }
}
