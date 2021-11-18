package org.solidcoding.results;

import static org.solidcoding.results.testutil.TestValue.TEST_CONTENT;
import static org.solidcoding.results.testutil.TestValue.TEST_MESSAGE;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.results.assertions.ResultAssertions;
import org.solidcoding.results.testutil.TestValue;

class ResultTests {

  @Test
  void success_shouldReturnSuccessResult() {
    ResultAssertions.assertThat(Result.success()).isValidSuccessResult()
                    .isEmpty();
  }

  @Test
  void success_shouldReturnSuccessResultWithContents() {
    ResultAssertions.assertThat(Result.success(TEST_CONTENT))
                    .isValidSuccessResult()
                    .containsContent(TEST_CONTENT);
  }

  @Test
  void emptyResource_shouldReturnSuccessResult() {
    ResultAssertions.assertThat(Result.emptyResource()).isValidSuccessResult()
                    .isEmpty();
  }

  @Test
  void emptyResource_withMessage_shouldReturnSuccessResult() {
    ResultAssertions.assertThat(Result.emptyResource(TestValue.TEST_MESSAGE))
                    .isValidSuccessResult()
                    .containsMessage(TestValue.TEST_MESSAGE);
  }

  @Test
  void notFound_shouldReturnNotFoundResult() {
    ResultAssertions.assertThat(Result.notFound()).isValidUnsuccessfulResult()
                    .isEmpty();
  }

  @Test
  void notFound_withMessage_shouldReturnUnsuccessfulResult() {
    ResultAssertions.assertThat(Result.notFound(TestValue.TEST_MESSAGE))
                    .isValidUnsuccessfulResult()
                    .containsMessage(TestValue.TEST_MESSAGE);
  }

  @Test
  void unprocessable_shouldReturnUnprocessableResult() {
    ResultAssertions.assertThat(Result.unprocessable(TestValue.TEST_MESSAGE))
                    .isValidUnsuccessfulResult()
                    .containsMessage(TestValue.TEST_MESSAGE);
  }

  @Test
  void unprocessable_withMessage_shouldReturnUnsuccessfulResult() {
    ResultAssertions.assertThat(Result.unprocessable(TestValue.TEST_MESSAGE))
                    .isValidUnsuccessfulResult()
                    .containsMessage(TestValue.TEST_MESSAGE);
  }

  @Test
  void unauthorized_shouldReturnunauthorizedResult() {
    ResultAssertions.assertThat(Result.unauthorized()).isValidUnsuccessfulResult()
                    .isEmpty();
  }

  @Test
  void unauthorized_withMessage_shouldReturnUnsuccessfulResult() {
    ResultAssertions.assertThat(Result.unauthorized(TestValue.TEST_MESSAGE))
                    .isValidUnsuccessfulResult()
                    .containsMessage(TestValue.TEST_MESSAGE);
  }

  @Test
  void errorOccurred_shouldReturnSuccessResult() {
    ResultAssertions.assertThat(Result.errorOccurred(TestValue.TEST_MESSAGE))
                    .isValidUnsuccessfulResult()
                    .containsMessage(TestValue.TEST_MESSAGE);
  }

  @Test
  void fromOptional_emptyOptional_shouldReturnEmptyResourceResult() {
    var result = Result.fromOptional(Optional.empty());
    ResultAssertions.assertThat(result).isValidSuccessResult()
                    .isEmpty();
  }

  @Test
  void fromOptional_filledOptional_shouldReturnSuccessResult() {
    var result = Result.fromOptional(Optional.of(TEST_CONTENT));
    ResultAssertions.assertThat(result).isValidSuccessResult()
                    .hasContent();
  }

  @Test
  void fromDelegate_SuccessfulRunnable_shouldReturnSuccessResult() {
    var result = Result.fromDelegate(() -> System.out.println(TEST_CONTENT));
    ResultAssertions.assertThat(result).isValidSuccessResult()
                    .isEmpty();
  }

  @Test
  void fromDelegate_ExceptionalRunnable_shouldReturnSuccessResult() {
    var result = Result.fromDelegate(() -> System.out.println(TEST_CONTENT));
    ResultAssertions.assertThat(result).isValidSuccessResult()
                    .isEmpty();
  }

  @Test
  void fromDelegate_SuccessfulPredicate_shouldReturnSuccessResult() {
    var result = Result.fromDelegate(x -> true, null);
    ResultAssertions.assertThat(result).isValidSuccessResult()
                    .isEmpty();
  }

  @Test
  void fromDelegate_UnsuccessfulPredicate_shouldReturnUnprocessableResult() {
    var result = Result.fromDelegate(x -> false, null);
    ResultAssertions.assertThat(result).isValidUnsuccessfulResult()
                    .isEmpty();
  }

  @Test
  void fromDelegate_ExceptionalPredicate_shouldReturnErrorOccurredResult() {
    var exception = new RuntimeException(TEST_CONTENT);
    var throwingPredicate = new Predicate<String>() {
      @Override
      public boolean test(String s) {
        throw exception;
      }
    };
    var message = exception.getMessage();
    var result = Result.fromDelegate(throwingPredicate, null);
    ResultAssertions.assertThat(result).isValidUnsuccessfulResult().containsMessage(message);
  }

  @Test
  void fromDelegate_SuccessfulSupplier_shouldReturnSuccessResult() {
    Supplier<String> supplier = () -> TEST_CONTENT;
    var result = Result.fromDelegate(supplier);
    ResultAssertions.assertThat(result)
                    .isValidSuccessResult()
                    .containsContent(TEST_CONTENT);
  }

  @Test
  void fromDelegate_UnsuccessfulSupplier_shouldReturnErrorOccurredResult() {
    var exception = new RuntimeException(TEST_CONTENT);
    var throwingRunnable = new Supplier<String>() {
      @Override
      public String get() {
        throw exception;
      }
    };
    var message = exception.getMessage();
    var result = Result.fromDelegate(throwingRunnable);
    ResultAssertions.assertThat(result).isValidUnsuccessfulResult().containsMessage(message);
  }

  @Test
  void transform_shouldReturnEmptyResultWithCorrectStatus() {
    var result = Result.success("test");
    Assertions.assertThat(result.isEmpty()).isFalse();
    var actual = Result.<Integer>transform(result);
    Assertions.assertThat(actual.isEmpty()).isTrue();
    Assertions.assertThat(actual.isSuccessful()).isTrue();
  }

  @Test
  void transform_withContent_shouldReturnGivenContent() {
    var result = Result.success("test");
    Assertions.assertThat(result.isEmpty()).isFalse();
    var newContent = new Object();
    var actual = Result.transform(result, newContent);
    Assertions.assertThat(actual.isSuccessfulWithContents()).isTrue();
    Assertions.assertThat(actual.getContents()).isEqualTo(newContent);
  }

  @Test
  void isEmpty_emptyResult_shouldReturnTrue() {
    var result = Result.emptyResource();
    Assertions.assertThat(result.isEmpty()).isTrue();
  }

  @Test
  void isEmpty_filledResult_shouldReturnFalse() {
    var result = Result.success("test");
    Assertions.assertThat(result.isEmpty()).isFalse();
  }

  @Test
  void isSuccessfulWithContents_successfulEmptyResult_shouldReturnFalse() {
    var result = Result.emptyResource();
    Assertions.assertThat(result.isSuccessfulWithContents()).isFalse();
  }

  @Test
  void isSuccessfulWithContents_successfulFilledResult_shouldReturnTrue() {
    var result = Result.success("test");
    Assertions.assertThat(result.isSuccessfulWithContents()).isTrue();
  }

  @Test
  void getResultStatus_shouldReturnResultStatus() {
    Assertions.assertThat(Result.success().getResultStatus()).isEqualTo(ResultStatus.SUCCESS);
    Assertions.assertThat(Result.errorOccurred(TEST_MESSAGE).getResultStatus())
              .isEqualTo(ResultStatus.ERROR_OCCURRED);
    Assertions.assertThat(Result.emptyResource().getResultStatus())
              .isEqualTo(ResultStatus.EMPTY_RESOURCE);
    Assertions.assertThat(Result.notFound().getResultStatus()).isEqualTo(ResultStatus.NOT_FOUND);
    Assertions.assertThat(Result.unauthorized().getResultStatus())
              .isEqualTo(ResultStatus.UNAUTHORIZED);
    Assertions.assertThat(Result.unprocessable().getResultStatus())
              .isEqualTo(ResultStatus.UNPROCESSABLE);
  }

  @Test
  void hasContents_withContents_shouldReturnTrue() {
    var result = Result.success(TEST_CONTENT);
    Assertions.assertThat(result.hasContents()).isTrue();
  }

  @Test
  void hasContents_withoutContents_shouldReturnFalse() {
    var result = Result.success();
    Assertions.assertThat(result.hasContents()).isFalse();
  }

  @Test
  void isEmpty_withContents_shouldReturnFalse() {
    var result = Result.success(TEST_CONTENT);
    Assertions.assertThat(result.isEmpty()).isFalse();
  }

  @Test
  void isEmpty_withoutContents_shouldReturnTrue() {
    var result = Result.success();
    Assertions.assertThat(result.isEmpty()).isTrue();
  }

  @Test
  void getOptionalContents_shouldReturnContentsAsOptional() {
    var result = Result.success(TEST_CONTENT);
    Assertions.assertThat(result.getOptionalContents().get()).isEqualTo(TEST_CONTENT);
  }

  @Test
  void getContents_shouldReturnContents() {
    var result = Result.success(TEST_CONTENT);
    Assertions.assertThat(result.getContents()).isEqualTo(TEST_CONTENT);
  }

  @Test
  void getMessage_shouldReturnMessage() {
    var errorMessage = "I am error";
    var errorResult = Result.errorOccurred(errorMessage);
    Assertions.assertThat(errorResult.getMessage()).isEqualTo(errorMessage);
    var successResult = Result.success();
    Assertions.assertThat(successResult.getMessage()).isEqualTo("Nothing to report");
  }
}
