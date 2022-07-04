package com.shanjib.messages.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class Message {
  private static final LocalDate JANUARY_2016 = LocalDate.of(2016, 1, 1);
  private static final LocalDate JANUARY_2017 = LocalDate.of(2017, 1, 1);
  private static final LocalDate JANUARY_2018 = LocalDate.of(2018, 1, 1);
  private static final LocalDate JANUARY_2019 = LocalDate.of(2019, 1, 1);
  private static final LocalDate JANUARY_2020 = LocalDate.of(2020, 1, 1);
  private static final LocalDate JANUARY_2021 = LocalDate.of(2021, 1, 1);

  private String sender_name;
  private Long timestamp_ms;
  private LocalDate date;
  private String content;
  private String type;
  private List<Reaction> reactions;
  private List<Photo> photos;

  public Integer getReactionCount() {
    if (reactions != null) {
      return reactions.size();
    }
    return 0;
  }

  public LocalDate getDate() {
    if (date == null) {
      date = Instant.ofEpochMilli(timestamp_ms).atZone(ZoneId.systemDefault()).toLocalDate();
    }
    return date;
  }

  public boolean isFrom2016() {
    return getDate().isAfter(JANUARY_2016) && getDate().isBefore(JANUARY_2017);
  }
  public boolean isFrom2017() {
    return getDate().isAfter(JANUARY_2017) && getDate().isBefore(JANUARY_2018);
  }
  public boolean isFrom2018() {
    return getDate().isAfter(JANUARY_2018) && getDate().isBefore(JANUARY_2019);
  }
  public boolean isFrom2019() {
    return getDate().isAfter(JANUARY_2019) && getDate().isBefore(JANUARY_2020);
  }
  public boolean isFrom2020() {
    return getDate().isAfter(JANUARY_2020) && getDate().isBefore(JANUARY_2021);
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
