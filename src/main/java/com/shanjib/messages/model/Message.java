package com.shanjib.messages.model;

import com.google.common.collect.Sets;
import com.shanjib.messages.util.DateUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class Message implements Comparable<Message> {
  private String sender_name;
  private Long timestamp_ms;
  private LocalDate date;
  private String content;
  private String type;
  private boolean is_unsent;
  private boolean is_taken_down;
  private Set<Reaction> reactions;
  private List<Media> photos;
  private List<Media> videos;
  private List<Media> gifs;
  private List<Media> files;

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
    return String.join("",
        getContent(),
        " - ", getSender_name(),
        ", ", getReactionCount().toString() + " reactions"
    );
  }

  private String getContent() {
    if (is_unsent) {
      return "unsent";
    }
    if (content != null) {
      return content;
    }
    if (photos != null) {
      return photos.stream().map(Media::getUri).collect(Collectors.joining(";"));
    }
    if (videos != null) {
      return videos.stream().map(Media::getUri).collect(Collectors.joining(";"));
    }
    if (gifs != null) {
      return gifs.stream().map(Media::getUri).collect(Collectors.joining(";"));
    }
    if (files != null) {
      return files.stream().map(Media::getUri).collect(Collectors.joining(";"));
    }
    return "something else";
  }

  @Override
  public int compareTo(Message message) {
    return Integer.compare(message.getReactionCount(), getReactionCount());
  }
}
