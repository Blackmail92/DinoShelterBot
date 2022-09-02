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
                    if (word.toLowerCase().startsWith("с")) {
                        String t = Character.isLowerCase(word.charAt(0)) ? "т" : "Т";
                        return channel.createMessage(t + word.substring(1));
                    } else {
                        String c = Character.isLowerCase(word.charAt(0)) ? "с" : "С";
                        return channel.createMessage(c + word.substring(1));
                    }
                }).then();
    }
}
