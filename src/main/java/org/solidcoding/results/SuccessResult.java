package org.solidcoding.results;

class SuccessResult<T> extends AbstractResult<T> {

  SuccessResult() {
    super(ResultStatus.SUCCESS);
  }

  SuccessResult(T contents) {
    super(ResultStatus.SUCCESS, contents);
  }

}
