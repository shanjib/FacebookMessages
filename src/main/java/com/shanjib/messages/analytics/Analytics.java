package com.shanjib.messages.analytics;

import com.shanjib.messages.model.Message;
import com.shanjib.messages.model.MessageFile;
import com.shanjib.messages.model.Participant;
import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Analytics {

  private final Map<Participant, ParticipantAnalytics> participantAnalyticsMap = new HashMap<>();
  private Message mostReactionMessage = new Message();
  private Integer errorMessageCount = 0;

  public String toString() {
    StringBuilder toString = new StringBuilder(ParticipantAnalytics.header());
    for (ParticipantAnalytics participantAnalytics : participantAnalyticsMap.values()) {
      toString.append("\r\n");
      toString.append(participantAnalytics.toString());
    }
    toString.append("\r\n");
    toString.append(errorMessageCount);
    toString.append("\r\n");
    toString.append(mostReactionMessage.getSender_name()).append(",");
    toString.append(mostReactionMessage.toString());
    return toString.toString();
  }

  public void runAnalytics(MessageFile messageFile) {
    populateMap(messageFile);
    for (Message message : messageFile.getMessages()) {
      runAnalytics(message);
      if (message.getReactionCount() > mostReactionMessage.getReactionCount()) {
        mostReactionMessage = message;
      }
    }
  }

  private void populateMap(MessageFile messageFile) {
    for (Participant participant : messageFile.getParticipants()) {
      if (!participantAnalyticsMap.containsKey(participant)) {
        participantAnalyticsMap.put(participant, new ParticipantAnalytics(participant.getName()));
      }
    }
  }

  private void runAnalytics(Message message) {
    Participant participant = new Participant(message.getSender_name());
    ParticipantAnalytics participantAnalytics = participantAnalyticsMap.get(participant);
    if (participantAnalytics == null) {
      System.out.println("error on message: " + message);
      errorMessageCount++;
      return;
    }

    participantAnalytics.incrementMessageCount();
    participantAnalytics.incrementReactionCount(message.getReactionCount());

    if (message.getReactionCount() > participantAnalytics.getMostReactionMessage().getReactionCount()) {
      participantAnalytics.setMostReactionMessage(message);
    }

    participantAnalyticsMap.put(participant, participantAnalytics);
  }
}
