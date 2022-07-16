package com.shanjib.messages.model;

import static com.shanjib.messages.util.DateUtil.*;

import com.google.common.collect.Sets;
import com.shanjib.messages.util.DateUtil;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class Message {
  private String sender_name;
  private Long timestamp_ms;
  private LocalDate date;
  private String content;
  private String type;
  private boolean is_unsent;
  private boolean is_taken_down;
  private Set<Reaction> reactions;
  private List<Photo> photos;

  public Integer getReactionCount() {
    return reactions != null ? reactions.size() : 0;
  }

  public Set<Reaction> getReactions() {
    return reactions != null ? reactions : Sets.newHashSet();
  }

  public LocalDate getDate() {
    if (date == null) {
      date = DateUtil.instantToLocalDate(timestamp_ms);
    }
    return date;
  }

  public String toString() {
    return String.join(",",
        getContent(),
        getReactionCount().toString()
    );
  }

  private String getContent() {
    if (content != null) {
      return content;
    }
    if (photos != null) {
      return photos.stream().map(Photo::getUri).collect(Collectors.joining(";"));
    }
    return "something else";
  }
}
