package world.solidcoding.results;

class ErrorOccurredResult<T> extends AbstractResult<T> {

  public ErrorOccurredResult(String message) {
    super(ResultStatus.ERROR_OCCURRED, message);
  }

}
