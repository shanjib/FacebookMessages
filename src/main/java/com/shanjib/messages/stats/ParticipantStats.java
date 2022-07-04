package com.shanjib.messages.stats;

import com.google.common.collect.Lists;
import com.shanjib.messages.model.Message;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Collection;
import lombok.Data;

@Data
public class ParticipantStats {

  private final String name;
  private final Collection<Message> messages;
  private final Collection<Message> messagesFrom2016;
  private final Collection<Message> messagesFrom2017;
  private final Collection<Message> messagesFrom2018;
  private final Collection<Message> messagesFrom2019;
  private final Collection<Message> messagesFrom2020;

  public ParticipantStats(String name, Collection<Message> messages) {
    this.name = name;
    this.messages = messages;
    this.messagesFrom2016 = Lists.newArrayList();
    this.messagesFrom2017 = Lists.newArrayList();
    this.messagesFrom2018 = Lists.newArrayList();
    this.messagesFrom2019 = Lists.newArrayList();
    this.messagesFrom2020 = Lists.newArrayList();

    for (Message message : messages) {
      if (message.isFrom2016()) this.messagesFrom2016.add(message);
      if (message.isFrom2017()) this.messagesFrom2017.add(message);
      if (message.isFrom2018()) this.messagesFrom2018.add(message);
      if (message.isFrom2019()) this.messagesFrom2019.add(message);
      if (message.isFrom2020()) this.messagesFrom2020.add(message);
   }
  }

  public static String header() {
    return "name,message count,reaction count";
  }

  public String calculate() {
    Integer reactionCount = 0;
    for (Message message : messages) {
      reactionCount += message.getReactionCount();
      if (message.getReactionCount() > 0) {
        String test = message.getReactions().get(0).getReaction();
        String test2 = Normalizer.normalize(test, Form.NFKD);

        byte[] b = { (byte)-16, (byte)-97, (byte)-104, (byte)-128 };
        String emoji = new String(test.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        String test3 = "asdf";
      }
    }

    return String.join(",",
        name,
        Integer.toString(messages.size()),
        reactionCount.toString()
    );
  }

}
