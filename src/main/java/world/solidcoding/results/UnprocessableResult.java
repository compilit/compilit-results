package world.solidcoding.results;

class UnprocessableResult<T> extends AbstractResult<T> {

  public UnprocessableResult() {
    super(ResultStatus.UNPROCESSABLE);
  }

  public UnprocessableResult(String message) {
    super(ResultStatus.UNPROCESSABLE, message);
  }

}
