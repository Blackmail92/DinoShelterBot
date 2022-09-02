package vd.martynov.dinoshelterbot.listeners.message;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {

    public Mono<Void> processGreet(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("https://c.tenor.com/W_u3Ncn-iiUAAAAC/отец-здарова.gif"))
                .then();
    }

    public Mono<Void> processSuTu(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap(channel -> {
                    String word = message.getContent();
                    if (word.startsWith("с")) {
                        return channel.createMessage(word.replaceFirst("с", "т"));
                    } else {
                        return channel.createMessage(word.replaceFirst("т", "с"));
                    }
                }).then();
    }
}
