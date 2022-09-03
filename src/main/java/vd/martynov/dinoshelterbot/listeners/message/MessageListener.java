package vd.martynov.dinoshelterbot.listeners.message;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;
import java.util.Random;

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
                .flatMap(channel ->  channel.createMessage(swapSuTu(message)))
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
