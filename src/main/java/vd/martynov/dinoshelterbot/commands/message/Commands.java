package vd.martynov.dinoshelterbot.commands.message;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import vd.martynov.dinoshelterbot.commands.Command;
import vd.martynov.dinoshelterbot.persist.entity.Inventory;
import vd.martynov.dinoshelterbot.persist.entity.Item;
import vd.martynov.dinoshelterbot.persist.service.InventoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Configuration
public class Commands {
    private final Map<String, Command> commandsMap = new HashMap<>();
    private final InventoryService inventoryService;
    private final Random random;

    @Autowired
    public Commands(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.random = new Random();

        commandsMap.put("inventory", this::inventoryCommand);
        commandsMap.put("sutu", this::suTuCommand);
        commandsMap.put("greet", this::greetingCommand);
    }


    @Bean
    public Map<String, Command> commandsMap() {
        return commandsMap;
    }

    private Mono<Void> greetingCommand(MessageCreateEvent event) {
        return Mono.just(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(getRandomGreet()))
                .then();
    }

    private String getRandomGreet() {
        List<String> greetings = List.of("https://c.tenor.com/W_u3Ncn-iiUAAAAC/отец-здарова.gif",
                "https://c.tenor.com/nFXaaZBGaRcAAAAC/hat-tip-greetings.gif",
                "https://c.tenor.com/pqqlX7Ha8PcAAAAC/hello-bob.gif",
                "https://c.tenor.com/rlWKA9-VamsAAAAC/despicable-me-gru.gif",
                "https://c.tenor.com/e02iqUN5EjgAAAAC/dumb-dumber.gif",
                "https://c.tenor.com/Y35j9akmzecAAAAC/merry-christmas-baby-funny-animals.gif");
        return greetings.get(random.nextInt(greetings.size()));
    }

    private Mono<Void> suTuCommand(MessageCreateEvent event) {
        String word = event.getMessage().getContent();
        String answer = (word.toLowerCase().startsWith("с")
                ? Character.isLowerCase(word.charAt(0)) ? "т" : "Т"
                : Character.isLowerCase(word.charAt(0)) ? "с" : "С")
                + word.substring(1);
        return Mono.just(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(answer))
                .then();
    }

    private Mono<Void> inventoryCommand(MessageCreateEvent event) {
        Message message = event.getMessage();
        List<User> mentions = message.getUserMentions();

        User user = mentions.isEmpty() ? message.getAuthor().orElseThrow() : mentions.get(0);
        EmbedCreateSpec.Builder builder = createInventoryEmbed(user);

        return Mono.just(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(builder.build()))
                .then();
    }

    private EmbedCreateSpec.Builder createInventoryEmbed(User user) {
        log.info("{} id: {}", user.getUsername(), user.getId().asLong());

        EmbedCreateSpec.Builder respBuilder = EmbedCreateSpec.builder()
                .author(user.getTag(), null, user.getAvatarUrl());
        if (user.isBot()) {
            return respBuilder.color(Color.CINNABAR).description("Ботам не нужны инвентари, кожаный ублюдок.");
        }

        Inventory inventory = inventoryService.getInventory(user.getId().asLong());
        if (inventory == null) {
            respBuilder.color(Color.CINNABAR).description("К сожалению, инвентарь еще пуст.");
        } else {
            List<Item> items = inventory.getItems();
            respBuilder.color(Color.of(115, 138, 219)).description("Содержимое инвентаря:");
            items.forEach(item -> respBuilder.addField(item.getName(), "x" + item.getQuantity(), false));
        }
        return respBuilder;
    }
}
