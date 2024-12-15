package com.shanjib.messages;

import com.shanjib.messages.model.Message;
import com.shanjib.messages.stats.Person;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Main {

  private static final String MESSAGE_PREFIX = "message_";
  private static final String MESSAGE_DIR = "";
  private static final String CHAT_NAME = "";

  private static final List<FileProcessor> PROCESSOR_LIST = new ArrayList<>();

  public static void main(final String[] args) {
    File messageDir = new File(MESSAGE_DIR);
    findMessages(messageDir, CHAT_NAME);

    List<Message> messages = new ArrayList<>();
    for (FileProcessor processor : PROCESSOR_LIST) {
      processor.createMessageFile();
      messages.addAll(processor.getMessages());
    }
    StatsProcessor statsProcessor = new StatsProcessor();
    statsProcessor.analyze(messages);
    log(statsProcessor.toStats());
    for (Person person : statsProcessor.getPeople().values().stream().sorted().collect(Collectors.toList())) {
      log();
      log(person.toStats());
    }
  }

  private static void findMessages(File file, String chat) {
    if (file.isDirectory()) {
      for (File f : Objects.requireNonNull(file.listFiles())) {
        findMessages(f, chat);
      }
    } else {
      if (file.getName().contains(MESSAGE_PREFIX) && file.getAbsolutePath().contains(chat)) {
        log("analyzing " + file.getAbsolutePath());
        FileProcessor processor = new FileProcessor(file);
        PROCESSOR_LIST.add(processor);
      }
    }
  }

  public static void log() {
    log("");
  }

  public static void log(String string) {
    System.out.println(string);
  }
}
