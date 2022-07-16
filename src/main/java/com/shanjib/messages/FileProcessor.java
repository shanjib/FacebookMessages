package com.shanjib.messages;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.shanjib.messages.model.Message;
import com.shanjib.messages.model.MessageFile;
import com.shanjib.messages.model.Reaction;
import com.sun.tools.javac.util.Pair;
import java.io.File;
import java.io.IOException;
import lombok.Data;
import org.apache.commons.io.FileUtils;

@Data
public class FileProcessor {
  private final File file;
  private MessageFile messageFile;
  private Gson gson = new Gson();

  private Multimap<String, Message> senderToMessages = ArrayListMultimap.create();
  private Multimap<String, Integer> senderToReactionsReceivedCount = ArrayListMultimap.create();
  private Multimap<String, Integer> giverToReactionsGivenCount = ArrayListMultimap.create();
  private Multimap<Pair<String, String>, Integer> reactionRelationshipCount = ArrayListMultimap.create();

  public void createMessageFile() {
    String fileContent = null;
    try {
      fileContent = FileUtils.readFileToString(this.file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.messageFile = gson.fromJson(fileContent, MessageFile.class);
    System.out.println("gson conversion done");
  }

  public void processMessages() {
    for (Message message : messageFile.getMessages()) {
      senderToMessages.put(message.getSender_name(), message);
      senderToReactionsReceivedCount.put(message.getSender_name(), message.getReactionCount());
      for (Reaction reaction : message.getReactions()) {
        giverToReactionsGivenCount.put(reaction.getActor(), 1);
        reactionRelationshipCount.put(new Pair<>(message.getSender_name(), reaction.getActor()), 1);
      }
    }
  }
}
