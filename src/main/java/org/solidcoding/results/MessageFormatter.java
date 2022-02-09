package org.solidcoding.results;

import static org.solidcoding.results.Message.NO_MESSAGE_AVAILABLE;

class MessageFormatter {

  private MessageFormatter() {
  }

  public static String formatMessage(String message, Object... formatArguments) {
    if (message == null)
      return NO_MESSAGE_AVAILABLE;
    if (formatArguments == null || formatArguments.length == 0)
      return message;
    return String.format(message, formatArguments);
  }

}
