# JUnit 5 Test Runner Demo

This is a demo project that shows how to use JUnit 5 to run test cases programmatically.

The [TestRunner](src/main/java/com/luojl/demo/TestRunner.java) runs test in four rounds. The first two rounds use JUnit 5 API to run JUnit 5 tests in two different orders, the actual execution order is expected. The second two rounds use JUnit 5 API to run JUnit 4 tests in two different orders, but the actual execution order is unexpected. Refer to the [TestRunner](src/main/java/com/luojl/demo/TestRunner.java) for defailts.

Sample output:

```shell
$ mvn compile exec:java -Dexec.mainClass="com.luojl.demo.TestRunner" -Dexec.classpathScope="test"

(...)

# round 1. expected order: C, B, A
[JUnit5] Running Test C
[JUnit5] Running Test B
[JUnit5] Running Test A

# round 2. expected order: A, C, B
[JUnit5] Running Test A
[JUnit5] Running Test C
[JUnit5] Running Test B

# round 3. expected order: C, B, A (failed)
[JUnit4] running Test A
[JUnit4] running Test B
[JUnit4] running Test C

# round 4. expected order: A, C, B (failed)
[JUnit4] running Test A
[JUnit4] running Test B
[JUnit4] running Test C

(...)
```

## Useful References

1. [LauncherDiscoveryRequestBuilder](https://junit.org/junit5/docs/5.0.0/api/org/junit/platform/launcher/core/LauncherDiscoveryRequestBuilder.html)
2. [Running JUnit Tests Programmatically, from a Java Application](https://www.baeldung.com/junit-tests-run-programmatically-from-java)
3. [Changing the classpath scope when running Java programs](https://www.mojohaus.org/exec-maven-plugin/examples/example-exec-or-java-change-classpath-scope.html)
