package org.solidcoding.results;

import java.util.List;

public interface ResultCombiner<T> {

  /**
   * @param result the next result you wish to combine with the previous.
   * @return ResultCombiner<T> to chain the next result.
   */
  ResultCombiner<T> with(Result<T> result);

  /**
   * Get a list of all contents of the passed results if all results were successful. Returns a
   * SuccessResult if, and only if all other results were successful.
   *
   * @return Result<List < T>>
   */
  Result<List<T>> merge();

  /**
   * Get the combined result without contents of all passed results. Returns a SuccessResult if, and
   * only if all other results were successful.
   *
   * @return Result<List < T>>
   */
  Result<T> sum();

}
