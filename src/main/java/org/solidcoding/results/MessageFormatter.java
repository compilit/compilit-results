package org.solidcoding.results;

import java.util.IllegalFormatException;

import static org.solidcoding.results.Message.MESSAGE_FORMAT_ERROR;
import static org.solidcoding.results.Message.NO_MESSAGE_AVAILABLE;

class MessageFormatter {

  private MessageFormatter() {
  }

  static String formatMessage(String message, Object... formatArguments) {
    if (message == null)
      return NO_MESSAGE_AVAILABLE;
    if (formatArguments == null || formatArguments.length == 0)
      return message;
    try {
      return String.format(message, formatArguments);
    } catch (IllegalFormatException exception) {
      return MESSAGE_FORMAT_ERROR + exception.getMessage();
    }
  }

}
