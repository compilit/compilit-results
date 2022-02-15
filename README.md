# solidcoding-results

Simple library to encapsulate and propagate processing results. Inspired by Results in the Rust programming
language. (https://doc.rust-lang.org/std/result/enum.Result.html)

Often when something deep in our code goes wrong, we have only our exceptions to rely on propagating error messages. But
what if what happens isn't an actual "exception"? Exceptions should be just that. Exceptional. For everything else a
simple Result will suffice. Using results also enables you to better avoid using exceptions as a control flow
mechanism (which is an antipattern).

Don't confuse server responses with Results. A 404 response can be wrapped in a "not found" result, but a "not found"
result does not necessarily mean that somewhere a server gave you a 404 server response. This means that it not always
straightforward to map a Result to a server response, be cautious.

# installation

Get this dependency with the latest version

```
    <dependency>
      <artifactId>solidcoding-results</artifactId>
      <groupId>org.solidcoding</groupId>
    </dependency>
```

# usage

Everything can be handled through the Result interface. Whenever you have some process that could possibly fail, make
sure that it returns a Result. Which Result should be returned can be chosen manually or by passing the process as a
function into the resultOf methods.

```
if (everythingWentWellInAVoidProcess()) {
  return Result.success();
}

if (everythingWentWellInAProcess()) {
  return Result.success(content);
}

if (something.doesNotMeetOurExpectations()) {
  return Result.unprocessable("Reason");
}

return Result.resultOf(() -> doSomethingDangerous());

return Result.<SomeOtherType>fromResult(Result.<OneType>errorOccured()); // Returns the error result, but without content.

return Result.fromResult(Result.errorOccured(), "some other content"); // Returns the error result, but with different content.

return Result.combine(result1).with(result2).with(result3).merge(); // Returns a Result<List<T>> containing all contents. But only if all results were successful.

return Result.combine(result1).with(result2).with(result3).sum(); // Returns a SuccessResult. But only if all results were successful.
```
