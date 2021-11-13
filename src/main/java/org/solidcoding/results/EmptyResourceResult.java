package org.solidcoding.results;

class EmptyResourceResult<T> extends AbstractResult<T> {

  public EmptyResourceResult() {
    super(ResultStatus.EMPTY_RESOURCE);
  }

  public EmptyResourceResult(String message) {
    super(ResultStatus.EMPTY_RESOURCE, null, message);
  }

}
