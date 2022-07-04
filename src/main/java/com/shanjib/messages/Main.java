package com.shanjib.messages;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class Main {

  private static final String MESSAGE_DIR = "/Users/istiaqueshanjib/Downloads/facebook/brownmambas/";
  private static final String MESSAGE_PREFIX = "message_1 copy";
  private static final FileProcessor PROCESSOR = new FileProcessor();

  public static void main(final String[] args) throws IOException {
    File messageDir = new File(MESSAGE_DIR);
    for (File file : Objects.requireNonNull(messageDir.listFiles())) {
      String filename = file.getName();
      if (filename.startsWith(MESSAGE_PREFIX)) {
        PROCESSOR.processFile(file);
      }
    }
    PROCESSOR.processStats();
  }
}
