package org.solidcoding.results;

import java.util.Optional;

abstract class AbstractResult<T> implements Result<T> {

  private static final String NOTHING_TO_REPORT = "Nothing to report";
  private final ResultStatus resultStatus;
  private final T contents;
  private final String message;

  protected AbstractResult(ResultStatus resultStatus) {
    this.resultStatus = resultStatus;
    this.contents = null;
    this.message = NOTHING_TO_REPORT;
  }

  protected AbstractResult(ResultStatus resultStatus, String message) {
    this.resultStatus = resultStatus;
    this.contents = null;
    this.message = message;
  }

  protected AbstractResult(ResultStatus resultStatus, T contents) {
    this.resultStatus = resultStatus;
    this.contents = contents;
    this.message = NOTHING_TO_REPORT;
  }

  protected AbstractResult(ResultStatus resultStatus, T contents, String message) {
    this.resultStatus = resultStatus;
    this.message = message;
    this.contents = contents;
  }

  @Override
  public boolean isSuccessful() {
    return resultStatus.equals(ResultStatus.SUCCESS)
        || resultStatus.equals(ResultStatus.EMPTY_RESOURCE);
  }

  @Override
  public boolean isUnsuccessful() {
    return !isSuccessful();
  }

  @Override
  public ResultStatus getResultStatus() {
    return resultStatus;
  }

  @Override
  public Optional<T> getContents() {
    return Optional.ofNullable(contents);
  }

  @Override
  public boolean hasContents() {
    return getContents().isPresent();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
