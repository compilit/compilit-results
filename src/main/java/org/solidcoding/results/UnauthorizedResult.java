package org.solidcoding.results;

class UnauthorizedResult<T> extends AbstractResult<T> {

  UnauthorizedResult() {
    super(ResultStatus.UNAUTHORIZED);
  }

  UnauthorizedResult(String message) {
    super(ResultStatus.UNAUTHORIZED, message);
  }

}
