package org.solidcoding.results;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.solidcoding.results.MessageFormatter.formatMessage;

public interface Result<T> {

  /**
   * A generic success result for a process or validation.
   *
   * @param <T> the type of the contents.
   * @return a success Result.
   */
  static <T> Result<T> success() {
    return new SuccessResult<>();
  }

  /**
   * A generic success result for a process or validation.
   *
   * @param contents the contents of the result.
   * @param <T>      the type of the contents.
   * @return a success Result with contents. Or an empty resource Result if the content is null.
   */
  static <T> Result<T> success(T contents) {
    if (contents == null) {
      return new EmptyResourceResult<>();
    }
    return new SuccessResult<>(contents);
  }

  /**
   * A generic result for when the client asks for a non-existent value in an existing resource.
   *
   * @param <T> the content type.
   * @return an empty resource Result without a message.
   */
  static <T> Result<T> emptyResource() {
    return new EmptyResourceResult<>();
  }

  /**
   * A generic result for when the client asks for a non-existent value in an existing resource.
   *
   * @param <T>             the content type.
   * @param message         the message you wish to propagate.
   * @param formatArguments the message arguments you with to replace the '%s' symbol with.
   * @return an empty resource Result with a message.
   */
  static <T> Result<T> emptyResource(String message, String... formatArguments) {
    var actualMessage = formatMessage(message, (Object[]) formatArguments);
    return new EmptyResourceResult<>(actualMessage);
  }

  /**
   * A generic result for when the client asks for a non-existent resource.
   *
   * @param <T> the content type.
   * @return a not found Result without a message.
   */
  static <T> Result<T> notFound() {
    return new NotFoundResult<>();
  }

  /**
   * A generic result for when the client asks for a non-existent resource.
   *
   * @param <T>             the content type.
   * @param message         the message you wish to propagate.
   * @param formatArguments the message arguments you with to replace the '%s' symbol with.
   * @return a not found Result with a message.
   */
  static <T> Result<T> notFound(String message, String... formatArguments) {
    var actualMessage = formatMessage(message, (Object[]) formatArguments);
    return new NotFoundResult<>(actualMessage);
  }

  /**
   * A generic failure result. Can be used for pretty much any failed process or validation.
   *
   * @param <T> the type of the contents.
   * @return an unprocessable Result without a message.
   */
  static <T> Result<T> unprocessable() {
    return new UnprocessableResult<>();
  }

  /**
   * A generic failure result. Can be used for pretty much any failed process or validation.
   *
   * @param message         the message you wish to propagate.
   * @param formatArguments the message arguments you with to replace the '%s' symbol with.
   * @param <T>             the type of the contents.
   * @return an unprocessable Result with a message.
   */
  static <T> Result<T> unprocessable(String message, String... formatArguments) {
    var actualMessage = formatMessage(message, (Object[]) formatArguments);
    return new UnprocessableResult<>(actualMessage);
  }

  /**
   * A generic result for any encountered authentication/authorization issue.
   *
   * @param <T> the content type.
   * @return an empty unauthorized Result without a message.
   */
  static <T> Result<T> unauthorized() {
    return new UnauthorizedResult<>();
  }

  /**
   * A generic result for any encountered authentication/authorization issue.
   *
   * @param message         the message you wish to propagate.
   * @param formatArguments the message arguments you with to replace the '%s' symbol with.
   * @param <T>             the content type.
   * @return an empty unauthorized Result with a message.
   */
  static <T> Result<T> unauthorized(String message, String... formatArguments) {
    var actualMessage = formatMessage(message, (Object[]) formatArguments);
    return new UnauthorizedResult<>(actualMessage);
  }

  /**
   * A generic result for any encountered exceptions.
   *
   * @param <T>             the content type.
   * @param message         the error message.
   * @param formatArguments the message arguments you with to replace the '%s' symbol with.
   * @return an error occurred Result with a message.
   */
  static <T> Result<T> errorOccurred(String message, String... formatArguments) {
    var actualMessage = formatMessage(message, (Object[]) formatArguments);
    return new ErrorOccurredResult<>(actualMessage);
  }

  /**
   * A generic result that encapsulates a runnable process. Returns a Success result if the runnable
   * does not throw an Exception.
   *
   * @param runnable the actual process.
   * @param <T>      the content type.
   * @return SuccessResult or ErrorOccurredResult with the exception message.
   */
  static <T> Result<T> fromDelegate(Runnable runnable) {
    try {
      runnable.run();
      return Result.success();
    } catch (Exception exception) {
      return Result.errorOccurred(exception.getMessage());
    }
  }

  /**
   * A generic result that encapsulates a supplying process. Returns a Success result with the
   * supplied content if the Supplier does not throw an Exception. If the Supplier throws an
   * Exception, it returns an ErrorOccurredResult with the exception message added to the Result.
   *
   * @param supplier the content-supplying function.
   * @param <T>      the type of the contents.
   * @return SuccessResult or ErrorOccurredResult with the exception message.
   */
  static <T> Result<T> fromDelegate(Supplier<T> supplier) {
    try {
      var result = supplier.get();
      return Result.success(result);
    } catch (Exception exception) {
      return Result.errorOccurred(exception.getMessage());
    }
  }

  /**
   * A generic result that encapsulates a predicate. Returns a Success result with the value if the
   * predicate resolves to true. And an Unprocessable result if it resolves to false. If the
   * Predicate throws an Exception, it returns an ErrorOccurredResult with the exception message
   * added to the Result.
   *
   * @param predicate the predicate which to apply to the value.
   * @param value     the value which needs to be tested by the predicate.
   * @param <T>       the type of the contents.
   * @return SuccessResult, UnprocessableResult or ErrorOccurredResult with the exception message.
   */
  static <T> Result<T> fromDelegate(Predicate<T> predicate, T value) {
    try {
      var result = predicate.test(value);
      if (result) {
        return Result.success(value);
      }
      return Result.unprocessable();
    } catch (Exception exception) {
      return Result.errorOccurred(exception.getMessage());
    }
  }

  /**
   * Transforms an existing Result into another one while retaining the status. Works as an adapter.
   * Example: pass an Integer Result, but return a String Result.
   *
   * @param result the existing Result.
   * @param <T>    the content type of the new Result.
   * @return Result.
   */
  static <T> Result<T> fromResult(Result<?> result) {
    var resultStatus = result.getResultStatus();
    var resultMessage = result.getMessage();
    return Result.of(resultStatus, resultMessage);
  }

  /**
   * Transforms an existing Result into another one with a different content while retaining the
   * status. Works as an adapter. Example: pass an Integer Result, but return a String Result.
   *
   * @param content the content you with to propagate.
   * @param result  the existing Result.
   * @param <T>     the content type of the new result.
   * @return Result.
   */
  static <T> Result<T> fromResult(Result<?> result, T content) {
    var resultStatus = result.getResultStatus();
    var resultMessage = result.getMessage();
    return Result.of(resultStatus, resultMessage, content);
  }

  /**
   * Transforms an existing Result into another one with a different content while retaining the
   * status. Works as an adapter. Example: pass an Integer Result, but return a String Result.
   *
   * @param message         the message you with to add.
   * @param formatArguments the message arguments you with to replace the '%s' symbol with.
   * @param result          the existing Result.
   * @param <T>             the content type of the new result.
   * @return Result.
   */
  static <T> Result<T> fromResult(Result<?> result, String message, String... formatArguments) {
    var resultStatus = result.getResultStatus();
    var actualMessage = formatMessage(message, (Object[]) formatArguments);
    return Result.of(resultStatus, actualMessage);
  }

  /**
   * Will return either a Success result or an EmptyResource result depending on whether the
   * Optional isPresent,
   *
   * @param optional the optional value of the result to return.
   * @param <T>      the content type of the new Result.
   * @return SuccessResult or EmptyResourceResult.
   */
  static <T> Result<T> fromOptional(Optional<T> optional) {
    return optional.map(Result::success).orElseGet(Result::emptyResource);
  }

  /**
   * @param <T>    the content type of the result.
   * @param result the result you wish to combine/merge/sum with others.
   * @return ResultCombiner to chain the next result.
   */
  static <T> ResultCombiner<T> combine(Result<T> result) {
    return new ResultToListCombiner<>(result);
  }

  private static <T> Result<T> of(ResultStatus resultStatus, String message) {
    return of(resultStatus, message, null);
  }

  private static <T> Result<T> of(ResultStatus resultStatus, String message, T content) {
    switch (resultStatus) {
      case SUCCESS:
        return Result.success(content);
      case UNAUTHORIZED:
        return Result.unauthorized(message);
      case NOT_FOUND:
        return Result.notFound(message);
      case EMPTY_RESOURCE:
        return Result.emptyResource(message);
      case ERROR_OCCURRED:
        return Result.errorOccurred(message);
      default:
        return Result.unprocessable(message);
    }
  }

  /**
   * @return the ResultStatus of the result.
   */
  ResultStatus getResultStatus();

  /**
   * @return true if the ResultStatus equals SUCCESS or EMPTY_RESOURCE.
   */
  boolean isSuccessful();

  /**
   * @return true if the ResultStatus equals SUCCESS or EMPTY_RESOURCE and has contents
   */
  boolean isSuccessfulWithContents();

  /**
   * @return true if the ResultStatus does not equal SUCCESS or EMPTY_RESOURCE.
   */
  boolean isUnsuccessful();

  /**
   * @return true if the Result has contents.
   */
  boolean hasContents();

  /**
   * @return true if the Result has no contents.
   */
  boolean isEmpty();

  /**
   * @return the contents of the result. Can be null
   */
  T getContents();

  /**
   * @return the optional contents of the result.
   */
  Optional<T> getOptionalContents();

  /**
   * @return the message of the result if present.
   */
  String getMessage();
}
