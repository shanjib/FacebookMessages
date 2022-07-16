package com.shanjib.messages;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.shanjib.messages.model.Message;
import com.sun.tools.javac.util.Pair;
import java.util.Collection;
import java.util.Collections;
import java.util.OptionalDouble;
import lombok.Data;

@Data
public class StatsProcessor {

  private Multimap<String, Message> senderToMessages = ArrayListMultimap.create();
  private Multimap<String, Integer> senderToReactionsReceivedCount = ArrayListMultimap.create();
  private Multimap<String, Integer> giverToReactionsGivenCount = ArrayListMultimap.create();
  private Multimap<Pair<String, String>, Integer> reactionRelationshipCount = ArrayListMultimap.create();

  public void addMaps(Multimap<String, Message> senderToMessages,
      Multimap<String, Integer> senderToReactionsReceivedCount,
      Multimap<String, Integer> giverToReactionsGivenCount,
      Multimap<Pair<String, String>, Integer> reactionRelationshipCount) {
    this.senderToMessages.putAll(senderToMessages);
    this.senderToReactionsReceivedCount.putAll(senderToReactionsReceivedCount);
    this.giverToReactionsGivenCount.putAll(giverToReactionsGivenCount);
    this.reactionRelationshipCount.putAll(reactionRelationshipCount);
  }

  public void calculateStats() {
    System.out.println("sender|total messages");
    for (String sender : senderToMessages.keySet()) {
      System.out.println(String.join("|",
          sender,
          Integer.toString(senderToMessages.get(sender).size())
          )
      );
    }
    System.out.println();
    System.out.println("sender|total reactions|average reactions|average reactions excluding 0|max reaction");
    for (String sender : senderToReactionsReceivedCount.keySet()) {
      Collection<Integer> reactionsReceived = senderToReactionsReceivedCount.get(sender);
      int sum = reactionsReceived.stream().mapToInt(a -> a).sum();
      OptionalDouble average = reactionsReceived.stream().mapToInt(a -> a).average();
      OptionalDouble averageExcluding0 = reactionsReceived.stream().mapToInt(a -> a).filter(a -> a > 0).average();
      System.out.println(String.join("|",
          sender,
          Integer.toString(sum),
          Double.toString(average.isPresent() ? average.getAsDouble() : 0d),
          Double.toString(averageExcluding0.isPresent() ? averageExcluding0.getAsDouble() : 0d),
          Integer.toString(Collections.max(reactionsReceived))
          )
      );
    }
    System.out.println();
    System.out.println("giver|total reactions|average reactions|average reactions excluding 0|max reaction");
    for (String giver : giverToReactionsGivenCount.keySet()) {
      System.out.println(String.join("|",
          giver,
          Integer.toString(giverToReactionsGivenCount.get(giver).size())
          )
      );
    }

    System.out.println();
    System.out.println("sender|reaction giver|total reactions|per message");
    for (Pair<String, String> senderToGiver : reactionRelationshipCount.keySet()) {
      Collection<Message> sentMessages = senderToMessages.get(senderToGiver.fst);
      System.out.println(String.join("|",
          senderToGiver.fst,
          senderToGiver.snd,
          Integer.toString(reactionRelationshipCount.get(senderToGiver).size()),
          Double.toString((double)reactionRelationshipCount.get(senderToGiver).size() / (double)sentMessages.size())
          )
      );
    }
  }

}
