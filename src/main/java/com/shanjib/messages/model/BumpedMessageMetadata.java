package com.shanjib.messages.model;

import lombok.Data;

@Data
public class BumpedMessageMetadata {
  private String bumped_message;
  private boolean is_bumped;
}
