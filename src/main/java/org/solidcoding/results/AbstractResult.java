package org.solidcoding.results;

import java.util.Optional;

import static org.solidcoding.results.Message.NOTHING_TO_REPORT;

abstract class AbstractResult<T> implements Result<T> {

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
  public boolean isSuccessfulWithContents() {
    return isSuccessful() && hasContents();
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
  public T getContents() {
    return contents;
  }

  @Override
  public Optional<T> getOptionalContents() {
    return Optional.ofNullable(contents);
  }

  @Override
  public boolean hasContents() {
    return getOptionalContents().isPresent();
  }

  @Override
  public boolean isEmpty() {
    return !hasContents();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
