package world.solidcoding.results;

class UnauthorizedResult<T> extends AbstractResult<T> {

  public UnauthorizedResult() {
    super(ResultStatus.UNAUTHORIZED);
  }

  public UnauthorizedResult(String message) {
    super(ResultStatus.UNAUTHORIZED, message);
  }

}
