package world.solidcoding.results;

import static world.solidcoding.results.testutil.TestValue.TEST_CONTENT;
import static world.solidcoding.results.testutil.TestValue.TEST_MESSAGE;

import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import world.solidcoding.results.assertions.ResultAssertions;

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
    ResultAssertions.assertThat(Result.emptyResource(TEST_MESSAGE))
                    .isValidSuccessResult()
        .containsMessage(TEST_MESSAGE);
  }

  @Test
  void notFound_shouldReturnNotFoundResult() {
    ResultAssertions.assertThat(Result.notFound()).isValidUnsuccessfulResult();
  }

  @Test
  void notFound_withMessage_shouldReturnUnsuccessfulResult() {
    ResultAssertions.assertThat(Result.notFound(TEST_MESSAGE))
                    .isValidUnsuccessfulResult()
                    .containsMessage(TEST_MESSAGE);
  }

  @Test
  void unprocessable_shouldReturnUnprocessableResult() {
    ResultAssertions.assertThat(Result.unprocessable(TEST_MESSAGE)).isValidUnsuccessfulResult();
  }

  @Test
  void unprocessable_withMessage_shouldReturnUnsuccessfulResult() {
    ResultAssertions.assertThat(Result.unprocessable(TEST_MESSAGE))
                    .isValidUnsuccessfulResult()
                    .containsMessage(TEST_MESSAGE);
  }

  @Test
  void unauthorized_shouldReturnunauthorizedResult() {
    ResultAssertions.assertThat(Result.unauthorized()).isValidUnsuccessfulResult();
  }

  @Test
  void unauthorized_withMessage_shouldReturnUnsuccessfulResult() {
    ResultAssertions.assertThat(Result.unauthorized(TEST_MESSAGE))
                    .isValidUnsuccessfulResult()
                    .containsMessage(TEST_MESSAGE);
  }


  @Test
  void errorOccurred_shouldReturnSuccessResult() {
    ResultAssertions.assertThat(Result.errorOccurred(TEST_MESSAGE)).isValidUnsuccessfulResult();
  }

  @Test
  void fromDelegate_SuccessfulDelegate_shouldReturnSuccessResult() {
    var result = Result.fromDelegate(() -> System.out.println(TEST_CONTENT));
    ResultAssertions.assertThat(result).isValidSuccessResult();
  }

  @Test
  void fromDelegate_UnsuccessfulDelegate_shouldReturnErrorOccurredResult() {
    Supplier<String> throwingRunnable = Mockito.mock(Supplier.class);
    var exception = new RuntimeException(TEST_CONTENT);
    var message = exception.getMessage();
    Mockito.doThrow(exception).when(throwingRunnable).get();
    var result = Result.fromDelegate(throwingRunnable);
    ResultAssertions.assertThat(result)
                    .isValidUnsuccessfulResult()
                    .containsMessage(message);
  }

}
