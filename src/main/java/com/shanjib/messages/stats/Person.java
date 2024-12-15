package com.shanjib.messages.stats;

import com.shanjib.messages.StatsProcessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends StatsProcessor implements Comparable<Person> {
  private final String name;
  private Map<String, Integer> reactionsGiven = new HashMap<>();

  public Person(String name) {
    super();
    this.name = name;
  }

  public void incrementReactions(String sender) {
    reactionsGiven.putIfAbsent(sender, 0);
    reactionsGiven.put(sender, reactionsGiven.get(sender) + 1);
  }

  public Integer getTotalReactionsGiven() {
    return reactionsGiven.values().stream().mapToInt(Integer::intValue).sum();
  }

  @Override
  public String toStats() {
    Entry<String, Integer> mostReactionsGiven = null;
    for (Entry<String, Integer> day : reactionsGiven.entrySet()) {
      if (mostReactionsGiven == null || mostReactionsGiven.getValue() < day.getValue()) {
        mostReactionsGiven = day;
      }
    }
    return name + "\n" +
        "reactions given- " + getTotalReactionsGiven() + "\n" +
        "most reactions given to " + (mostReactionsGiven != null ? mostReactionsGiven.getKey() : "") + " with " + (mostReactionsGiven != null ? mostReactionsGiven.getValue() : "null") + "\n" +
        super.toStats();
  }

  @Override
  public int compareTo(Person person) {
    return String.CASE_INSENSITIVE_ORDER.compare(name, person.getName());
  }
}
