# solidcoding-results
Simple library to encapsulate and propagate processing results

# installation
Get this dependency with the latest version
```
    <dependency>
      <artifactId>solidcoding-results</artifactId>
      <groupId>world.solidcoding</groupId>
      <version>1.0.0.RELEASE</version>
    </dependency>
```

# usage
Everything can be handled through the Result interface. Whenever you have some process that could possibly fail, make sure that it returns a Result. Which Result should be returned can be chosen manually or by passing it an Exception throwing delegate.

```
if (everythingWentWellInAVoidProcess()) {
  return Result.success();
}

if (everythingWentWellInAProcess()) {
  return Result.success(content);
}

if (something.doesNotMeetOurExpectations) {
  return Result.unprocessable("Reason");
}

return Result.fromDelegate(() -> doSomethingDangerous());
```