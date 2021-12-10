package org.solidcoding.results;

import java.util.ArrayList;
import java.util.List;

class ResultToListCombiner<T> implements ResultCombiner<T> {

  private final List<T> resultList = new ArrayList<>();
  private final List<String> messages = new ArrayList<>();
  private boolean isSuccessful = true;

  ResultToListCombiner(Result<T> result) {
    resolve(result);
  }

  public ResultCombiner<T> with(Result<T> result) {
    resolve(result);
    return this;
  }

  public Result<List<T>> merge() {
    var finalMessage = prepareMessage();
    return isSuccessful
            ? Result.success(resultList)
            : Result.unprocessable(finalMessage);
  }

  public Result<T> sum() {
    var finalMessage = prepareMessage();
    return isSuccessful
            ? Result.success()
            : Result.unprocessable(finalMessage);
  }

  private String prepareMessage() {
    var stringBuilder = new StringBuilder("At least one Result was not successful, ");
    for (int index = 0; index < messages.size(); index++) {
      var message = messages.get(index);
      stringBuilder.append("message-").append(index + 1).append(": ").append(message);
      if (index == messages.size() - 1) {
        return stringBuilder.toString();
      }
      stringBuilder.append(", ");
    }
    return stringBuilder.toString();
  }

  private void resolve(Result<T> result) {
    if (result.hasContents()) {
      resultList.add(result.getContents());
    }
    if (result.isUnsuccessful()) {
      isSuccessful = false;
      if (result.getMessage() != null) {
        messages.add(result.getMessage());
      }
    }
  }

}
