package org.solidcoding.results;

class NotFoundResult<T> extends AbstractResult<T> {

  public NotFoundResult() {
    super(ResultStatus.NOT_FOUND);
  }

  public NotFoundResult(String message) {
    super(ResultStatus.NOT_FOUND, message);
  }

}

