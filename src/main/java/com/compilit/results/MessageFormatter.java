package com.compilit.results;

import java.util.IllegalFormatException;

class MessageFormatter {

  private MessageFormatter() {
  }

  static String formatMessage(String message, Object... formatArguments) {
    if (message == null)
      return Message.NO_MESSAGE_AVAILABLE;
    if (formatArguments == null || formatArguments.length == 0)
      return message;
    try {
      return String.format(message, formatArguments);
    } catch (IllegalFormatException exception) {
      return Message.MESSAGE_FORMAT_ERROR + exception.getMessage();
    }
  }

}
