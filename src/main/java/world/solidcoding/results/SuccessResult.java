package world.solidcoding.results;

class SuccessResult<T> extends AbstractResult<T> {

  public SuccessResult() {
    super(ResultStatus.SUCCESS);
  }

  public SuccessResult(T contents) {
    super(ResultStatus.SUCCESS, contents);
  }

}
