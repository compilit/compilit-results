package org.solidcoding.results;

class ErrorOccurredResult<T> extends AbstractResult<T> {

  ErrorOccurredResult(String message) {
    super(ResultStatus.ERROR_OCCURRED, message);
  }

}
