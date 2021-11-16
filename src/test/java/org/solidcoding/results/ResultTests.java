package org.solidcoding.results;

import static org.solidcoding.results.testutil.TestValue.TEST_CONTENT;

import java.util.function.Predicate;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.solidcoding.results.assertions.ResultAssertions;
import org.solidcoding.results.testutil.TestValue;

class ResultTests {

  @Test
  void success_shouldReturnSuccessResult() {
    ResultAssertions.assertThat(Result.success()).isValidSuccessResult();
  }

  @Test
  void success_shouldReturnSuccessResultWithContents() {
    ResultAssertions.assertThat(Result.success(TEST_CONTENT))
                    .isValidSuccessResult()
                    .containsContent(TEST_CONTENT);
  }

  @Test
  void emptyResource_shouldReturnSuccessResult() {
    ResultAssertions.assertThat(Result.emptyResource())
                    .isValidSuccessResult();
  }

  @Test
  void emptyResource_withMessage_shouldReturnSuccessResult() {
    ResultAssertions.assertThat(Result.emptyResource(TestValue.TEST_MESSAGE))
                    .isValidSuccessResult()
                    .containsMessage(TestValue.TEST_MESSAGE);
  }

  @Test
  void notFound_shouldReturnNotFoundResult() {
    ResultAssertions.assertThat(Result.notFound()).isValidUnsuccessfulResult();
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
                    .isValidUnsuccessfulResult();
  }

  @Test
  void unprocessable_withMessage_shouldReturnUnsuccessfulResult() {
    ResultAssertions.assertThat(Result.unprocessable(TestValue.TEST_MESSAGE))
                    .isValidUnsuccessfulResult()
                    .containsMessage(TestValue.TEST_MESSAGE);
  }

  @Test
  void unauthorized_shouldReturnunauthorizedResult() {
    ResultAssertions.assertThat(Result.unauthorized()).isValidUnsuccessfulResult();
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
                    .isValidUnsuccessfulResult();
  }

  @Test
  void fromDelegate_SuccessfulRunnable_shouldReturnSuccessResult() {
    var result = Result.fromDelegate(() -> System.out.println(TEST_CONTENT));
    ResultAssertions.assertThat(result).isValidSuccessResult();
  }

  @Test
  void fromDelegate_ExceptionalRunnable_shouldReturnSuccessResult() {
    var result = Result.fromDelegate(() -> System.out.println(TEST_CONTENT));
    ResultAssertions.assertThat(result).isValidSuccessResult();
  }

  @Test
  void fromDelegate_SuccessfulPredicate_shouldReturnSuccessResult() {
    var result = Result.fromDelegate(x -> true, null);
    ResultAssertions.assertThat(result).isValidSuccessResult();
  }

  @Test
  void fromDelegate_UnsuccessfulPredicate_shouldReturnUnprocessableResult() {
    var result = Result.fromDelegate(x -> false, null);
    ResultAssertions.assertThat(result).isValidUnsuccessfulResult();
  }

  @Test
  void fromDelegate_ExceptionalPredicate_shouldReturnErrorOccurredResult() {
    Predicate<String> throwingPredicate = Mockito.mock(Predicate.class);
    var exception = new RuntimeException(TEST_CONTENT);
    var message = exception.getMessage();
    Mockito.doThrow(exception).when(throwingPredicate).test(Mockito.any());
    var result = Result.fromDelegate(throwingPredicate, null);
    ResultAssertions.assertThat(result)
                    .isValidUnsuccessfulResult()
                    .containsMessage(message);
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
    Supplier<String> throwingRunnable = Mockito.mock(Supplier.class);
    var exception = new RuntimeException(TEST_CONTENT);
    var message = exception.getMessage();
    Mockito.doThrow(exception).when(throwingRunnable).get();
    var result = Result.fromDelegate(throwingRunnable);
    ResultAssertions.assertThat(result)
                    .isValidUnsuccessfulResult()
                    .containsMessage(message);
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
  
}
