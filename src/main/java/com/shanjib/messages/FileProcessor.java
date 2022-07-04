package com.shanjib.messages;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shanjib.messages.model.Message;
import com.shanjib.messages.model.MessageFile;
import com.shanjib.messages.stats.ParticipantStats;
import java.io.File;
import java.io.IOException;
import lombok.Data;
import org.apache.commons.io.FileUtils;

@Data
public class FileProcessor {

  public final Multimap<String, Message> participantMessageMultimap = ArrayListMultimap.create();

  public void processFile(File file) throws IOException {
    print("Processing file " + file.getName());
    String content = FileUtils.readFileToString(file);

    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    MessageFile messageFile = gson.fromJson(content, MessageFile.class);
    for (Message message : messageFile.getMessages()) {
      participantMessageMultimap.put(message.getSender_name(), message);
    }
  }

  public void processStats() {
    print(ParticipantStats.header());
    for (String participant : participantMessageMultimap.keySet()) {
      ParticipantStats stats = new ParticipantStats(participant, participantMessageMultimap.get(participant));
      print(stats.calculate());
    }
  }

  private void print(String line) {
    System.out.println(line);
  }
}
