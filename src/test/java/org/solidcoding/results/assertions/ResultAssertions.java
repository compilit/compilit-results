package org.solidcoding.results.assertions;

import org.assertj.core.api.AbstractAssert;
import org.solidcoding.results.Result;

public class ResultAssertions<T> extends AbstractAssert<ResultAssertions<T>, Result<T>> {


  protected ResultAssertions(Result<T> result) {
    super(result, ResultAssertions.class);
  }

  public static <T> ResultAssertions<T> assertThat(Result<T> actual) {
    return new ResultAssertions<>(actual);
  }

  public ResultAssertions<T> isValidSuccessResult() {
    if (actual.isUnsuccessful()) {
      failWithMessage("Expected Result to be successful");
    }
    return this;
  }

  public ResultAssertions<T> hasContent() {
    if (!actual.hasContents()) {
      failWithMessage("Expected Result to have content but was empty");
    }
    return this;
  }

  public ResultAssertions<T> isEmpty() {
    if (!actual.isEmpty()) {
      failWithMessage("Expected Result to have no content but it did");
    }
    return this;
  }

  public ResultAssertions<T> containsContent(T content) {
    if (!actual.hasContents()) {
      failWithMessage("Expected Result to have content but was empty");
    }
    if (!actual.getOptionalContents().get().equals(content)) {
      failWithMessage("Expected Result to have content equal to %s but was %s",
              content,
              actual.getContents());
    }
    return this;
  }

  public ResultAssertions<T> isValidUnsuccessfulResult() {
    if (actual.isSuccessful()) {
      failWithMessage("Expected Result to be unsuccessful");
    }
    return this;
  }

  public ResultAssertions<T> containsMessage(String message) {
    if (!actual.getMessage().equals(message)) {
      failWithMessage("Expected Result to have a message equal to %s but was %s",
              message,
              actual.getMessage());
    }
    return this;
  }

}
