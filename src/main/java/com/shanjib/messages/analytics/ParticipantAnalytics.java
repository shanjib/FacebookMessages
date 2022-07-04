package com.shanjib.messages.analytics;

import com.shanjib.messages.model.Message;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ParticipantAnalytics {
  @NonNull
  private String name;
  private Integer messageCount = 0;
  private Integer reactionCount = 0;
  private Message mostReactionMessage = new Message();

  public void incrementMessageCount() {
    messageCount++;
  }

  public void incrementReactionCount(Integer reactionCount) {
    this.reactionCount += reactionCount;
  }


  public String toString() {
    return String.join(",",
        name,
        messageCount.toString(),
        reactionCount.toString(),
        mostReactionMessage.toString()
    );
  }

  public static String header() {
    return "name,message count,total reaction count,most reacted message";
  }
}
