package com.shanjib.messages;

import com.shanjib.messages.model.Message;
import com.shanjib.messages.model.Reaction;
import com.shanjib.messages.stats.Person;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class StatsProcessor {
  private List<Message> messages = new ArrayList<>();
  private Map<String, Person> people = new HashMap<>();
  private Map<LocalDate, Integer> dateToMessages = new HashMap<>();
  private Integer messageCount = 0;
  private Integer messageWithReactionsCount = 0;
  private Integer reactionsReceivedCount = 0;
  private Integer messageDeletedCount = 0;

  public void analyze(List<Message> messages) {
    for (Message message : messages) {
      if (message.getDate().getYear() != 2024) continue;

      String sender = message.getSender_name();
      people.putIfAbsent(sender, new Person(sender));
      Person person = people.get(sender);
      person.addMessage(message);
      addMessage(message);

      for (Reaction reaction : message.getReactions()) {
        String reactor = reaction.getActor();
        people.putIfAbsent(reactor, new Person(reactor));
        Person actor = people.get(reactor);
        actor.incrementReactions(sender);
      }
    }
  }

  public void addMessage(Message message) {
    messages.add(message);
    messageCount++;
    if (message.getReactionCount() > 0) {
      messageWithReactionsCount++;
      reactionsReceivedCount += message.getReactionCount();
    }

    if (message.is_unsent()) messageDeletedCount++;

    dateToMessages.putIfAbsent(message.getDate(), 0);
    dateToMessages.put(message.getDate(), dateToMessages.get(message.getDate()) + 1);

  }

  public String getMostReactedMessages() {
    return messages.stream().sorted().limit(5)
        .map(Message::toString)
        .collect(Collectors.joining("\n\t"));
  }

  public String toStats() {
    Entry<LocalDate, Integer> mostActiveDay = null;
    for (Entry<LocalDate, Integer> day : dateToMessages.entrySet()) {
      if (mostActiveDay == null || mostActiveDay.getValue() < day.getValue()) {
        mostActiveDay = day;
      }
    }
    DecimalFormat df = new DecimalFormat("####0.0000");
    return String.join("\n",
        "total messages sent- " + messageCount,
        "total reactions received- " + reactionsReceivedCount,
        "avg reactions- " + df.format((double)reactionsReceivedCount / (double)messageCount),
        "messages deleted- " + messageDeletedCount,
        "most active day was " + mostActiveDay.getKey() + ", with " + mostActiveDay.getValue() + " messages",
        "top 5 messages- \n\t" + getMostReactedMessages()
        );
  }

}
