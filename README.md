# solidcoding-results

Simple library to encapsulate and propagate processing results.

Often when something deep in our code goes wrong, we have only our exceptions to rely on propagating
error messages. But what if what happens isn't an actual "exception"? Exceptions should be just
that. Exceptional. For everything else a simple Result will suffice.

# installation

Get this dependency with the latest version

```
    <dependency>
      <artifactId>solidcoding-results</artifactId>
      <groupId>org.solidcoding</groupId>
    </dependency>
```

# usage

Everything can be handled through the Result interface. Whenever you have some process that could
possibly fail, make sure that it returns a Result. Which Result should be returned can be chosen
manually or by passing the process as a function into the fromDelegate methods.

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

return Result.fromDelegate(() -> doSomethingDangerous());

return Result.<SomeOtherType>transform(Result.<OneType>errorOccured()); // return the error result, but without content.

return Result.transform(Result.errorOccured(), "some other content"); // return the error result, but with different content.
```
