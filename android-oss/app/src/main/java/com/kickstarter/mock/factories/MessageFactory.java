package com.kickstarter.mock.factories;

import com.kickstarter.models.Message;

import org.joda.time.DateTime;

import androidx.annotation.NonNull;

public final class MessageFactory {
  private MessageFactory() {}

  public static @NonNull Message message() {
    return Message.builder()
      .body("")
      .createdAt(DateTime.now())
      .id(123943059)
      .recipient(UserFactory.creator())
      .sender(UserFactory.user())
      .build();
  }
}
