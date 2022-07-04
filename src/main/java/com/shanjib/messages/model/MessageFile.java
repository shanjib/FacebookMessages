package com.shanjib.messages.model;

import java.util.List;
import lombok.Data;

@Data
public class MessageFile {
  private List<Participant> participants;
  private List<Message> messages;
  private String title;
  private boolean isStillParticipant;
  private String threadType;
  private String threadPath;
}
