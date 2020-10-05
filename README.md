# JUnit 5 Test Runner Demo

This is a demo project that shows how to use JUnit 5 to run test cases programmatically.

The [TestRunner](src/main/java/com/luojl/demo/TestRunner.java) runs test in four rounds. The first two rounds use JUnit 5 API to run JUnit 5 tests in two different orders, the actual execution order is expected. The second two rounds use JUnit 5 API to run JUnit 4 tests in two different orders, but the actual execution order is unexpected. Refer to [TestRunnerTest](src/test/java/com/luojl/demo/TestRunnerTest.java) for details.

## Useful References

1. [LauncherDiscoveryRequestBuilder](https://junit.org/junit5/docs/5.0.0/api/org/junit/platform/launcher/core/LauncherDiscoveryRequestBuilder.html)
2. [Running JUnit Tests Programmatically, from a Java Application](https://www.baeldung.com/junit-tests-run-programmatically-from-java)
3. [Changing the classpath scope when running Java programs](https://www.mojohaus.org/exec-maven-plugin/examples/example-exec-or-java-change-classpath-scope.html)
