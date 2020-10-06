# JUnit 5 Test Runner Demo

This is a demo project that shows how to use JUnit 5 to run test cases programmatically.

[TestRunner](src/main/java/com/luojl/demo/TestRunner.java) runs tests with JUnit 5 API.

[TestRunnerTest](src/test/java/com/luojl/demo/TestRunnerTest.java) use *TestRunner* to run tests. Some observations:

1. For JUnit 5 tests in same class, the execution order is the same with order in the given *list*. Refer to [TestRunnerTest.runJUnit5TestsRound1](src/test/java/com/luojl/demo/TestRunnerTest.java#L11) and [TestRunnerTest.runJUnit5TestsRound2](src/test/java/com/luojl/demo/TestRunnerTest.java#L22) for details.
2. For JUnit 5 tests with different class, tests in same class will be grouped together and test order in the same class is preserved. Refer to [TestRunnerTest.runJUnit5TestsRound1](src/test/java/com/luojl/demo/TestRunnerTest.java#L11) and [TestRunnerTest.runJUnit5TestsInterleaved](src/test/java/com/luojl/demo/TestRunnerTest.java#L33) for details.
3. If we run JUnit 4 tests with JUnit 5 API, the execution order is sorted alphabetically according to the test method names. Refer to [TestRunnerTest.runJUnit4TestsRound1](src/test/java/com/luojl/demo/TestRunnerTest.java#L11) and [TestRunnerTest.runJUnit5TestsInterleaved](src/test/java/com/luojl/demo/TestRunnerTest.java#L54) for details.
4. [Disable](https://junit.org/junit5/docs/5.7.0-M1/api/org.junit.jupiter.api/org/junit/jupiter/api/Disabled.html)d test will be skipped. Refer to [TestRunnerTest.runDisableTests](src/test/java/com/luojl/demo/TestRunnerTest.java#L70)
5. If JUnit 5 tests are annotated by [Order](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Order.html), the actual order is dominated by the order value of each tests. Refer to [TestRunnerTest.runTestsWithOrderAnnotation](src/test/java/com/luojl/demo/TestRunnerTest.java#L90) and [TestRunnerTest.runTestsWithOrderAnnotationMultipleClasses](src/test/java/com/luojl/demo/TestRunnerTest.java#L107)

## Useful References

1. [LauncherDiscoveryRequestBuilder](https://junit.org/junit5/docs/5.0.0/api/org/junit/platform/launcher/core/LauncherDiscoveryRequestBuilder.html)
2. [Running JUnit Tests Programmatically, from a Java Application](https://www.baeldung.com/junit-tests-run-programmatically-from-java)
3. [Changing the classpath scope when running Java programs](https://www.mojohaus.org/exec-maven-plugin/examples/example-exec-or-java-change-classpath-scope.html)
