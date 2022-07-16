package com.shanjib.messages;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class Main {

  private static final String MESSAGE_DIR = "/Users/istiaqueshanjib/Downloads/facebook/2022-06 Request/facebook-ishanjib(3)/messages/inbox/brownmambasfuckbu_k3wwhhyjpw/";
  private static final String MESSAGE_PREFIX = "message_";

  public static void main(final String[] args) {
    File messageDir = new File(MESSAGE_DIR);
    StatsProcessor statsProcessor = new StatsProcessor();
    for (File file : Objects.requireNonNull(messageDir.listFiles())) {
      String filename = file.getName();
      System.out.println("processing " + filename);
      if (file.isFile() && filename.startsWith(MESSAGE_PREFIX)) {
        FileProcessor processor = new FileProcessor(file);
        processor.createMessageFile();
        processor.processMessages();
        statsProcessor.addMaps(processor.getSenderToMessages(),
            processor.getSenderToReactionsReceivedCount(),
            processor.getGiverToReactionsGivenCount(),
            processor.getReactionRelationshipCount());
      }
    }
    System.out.println();
    statsProcessor.calculateStats();
  }
}
