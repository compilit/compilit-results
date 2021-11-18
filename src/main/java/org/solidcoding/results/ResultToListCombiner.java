package org.solidcoding.results;

import java.util.ArrayList;
import java.util.List;

class ResultToListCombiner<T> implements ResultCombiner<T> {

  private final List<T> resultList = new ArrayList<T>();
  private boolean isSuccessful = true;

  ResultToListCombiner(Result<T> result) {
    resolve(result);
  }

  public ResultCombiner<T> with(Result<T> result) {
    resolve(result);
    return this;
  }

  public Result<List<T>> merge() {
    return isSuccessful
        ? Result.success(resultList)
        : Result.unprocessable("At least one Result was not successful");
  }

  public Result<T> sum() {
    return isSuccessful
        ? Result.success()
        : Result.unprocessable("At least one Result was not successful");
  }

  private void resolve(Result<T> result) {
    if (result.hasContents()) {
      resultList.add(result.getContents());
    }
    if (result.isUnsuccessful()) {
      isSuccessful = false;
    }
  }

}
