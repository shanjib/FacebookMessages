package com.shanjib.messages;

import com.google.gson.Gson;
import com.shanjib.messages.model.Message;
import com.shanjib.messages.model.MessageFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.Data;
import org.apache.commons.io.FileUtils;

@Data
public class FileProcessor {
  private final File file;
  private MessageFile messageFile;
  private Gson gson = new Gson();

  public void createMessageFile() {
    String fileContent = null;
    try {
      fileContent = FileUtils.readFileToString(this.file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.messageFile = gson.fromJson(fileContent, MessageFile.class);
  }

  public List<Message> getMessages() {
    return messageFile.getMessages();
  }
}
