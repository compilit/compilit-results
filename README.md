# solidcoding-results
Simple library to encapsulate and propagate processing results

# installation
Get this dependency with the latest version
```
    <dependency>
      <artifactId>solidcoding-results</artifactId>
      <groupId>org.solidcoding</groupId>
    </dependency>
```

# usage
Everything can be handled through the Result interface. Whenever you have some process that could possibly fail, make sure that it returns a Result. Which Result should be returned can be chosen manually or by passing the process as a function into the fromDelegate methods.

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
```
