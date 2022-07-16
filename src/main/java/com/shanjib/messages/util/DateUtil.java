package com.shanjib.messages.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtil {
  public static final LocalDate JANUARY_2016 = LocalDate.of(2016, 1, 1);
  public static final LocalDate JANUARY_2017 = LocalDate.of(2017, 1, 1);
  public static final LocalDate JANUARY_2018 = LocalDate.of(2018, 1, 1);
  public static final LocalDate JANUARY_2019 = LocalDate.of(2019, 1, 1);
  public static final LocalDate JANUARY_2020 = LocalDate.of(2020, 1, 1);
  public static final LocalDate JANUARY_2021 = LocalDate.of(2021, 1, 1);

  public static LocalDate instantToLocalDate(Long instant) {
    return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static boolean isFrom2016(LocalDate date) {
    return date.isAfter(JANUARY_2016) && date.isBefore(JANUARY_2017);
  }
  public static boolean isFrom2017(LocalDate date) {
    return date.isAfter(JANUARY_2017) && date.isBefore(JANUARY_2018);
  }
  public static boolean isFrom2018(LocalDate date) {
    return date.isAfter(JANUARY_2018) && date.isBefore(JANUARY_2019);
  }
  public static boolean isFrom2019(LocalDate date) {
    return date.isAfter(JANUARY_2019) && date.isBefore(JANUARY_2020);
  }
  public static boolean isFrom2020(LocalDate date) {
    return date.isAfter(JANUARY_2020) && date.isBefore(JANUARY_2021);
  }
}
