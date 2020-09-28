package com.luojl.demo;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.discovery.MethodSelector;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class TestRunner {

    public static void runMultipleTestMethods(List<MethodSelector> methods,
            TestExecutionListener listener) {
        // The execution order is same as order in 'methods' variable.
        // So changing the order in 'methods' variable can change the test order
        // (the above statement is true for JUnit 5 test cases, but not for JUnit 4)
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(methods).build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);
        launcher.execute(testPlan, listener);
    }

    private static List<MethodSelector> toMethodSelectors(List<String> fullQualifiedMethodNames) {
        // The item in the list should be full qualified method name,
        // i.e., <package>.<class>#<method>
        return fullQualifiedMethodNames.stream().map(s -> DiscoverySelectors.selectMethod(s))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        PrintWriter stdOutWriter = new PrintWriter(System.out);

        // [First round] The actual order is expected and it's C, B, A
        List<String> junit5TestCases = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#TestB",
                "com.luojl.demo.JUnit5DemoTest#TestA");
        runMultipleTestMethods(toMethodSelectors(junit5TestCases), listener);
        listener.getSummary().printTo(stdOutWriter);

        // [Second round] The actual order is expected and it's A, C, B
        List<String> junit5TestCasesV2 = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestA",
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#TestB");
        runMultipleTestMethods(toMethodSelectors(junit5TestCasesV2), listener);
        listener.getSummary().printTo(stdOutWriter);

        // [Third round] the actual order is unexpected
        // Actual: A, B, C; Expected: C, B, A
        List<String> junit4TestCases = Arrays.asList(
                "com.luojl.demo.JUnit4DemoTest#TestC4",
                "com.luojl.demo.JUnit4DemoTest#TestB4",
                "com.luojl.demo.JUnit4DemoTest#TestA4");
        runMultipleTestMethods(toMethodSelectors(junit4TestCases), listener);
        listener.getSummary().printTo(stdOutWriter);

        // [Fourth round] the actual order is unexpected
        // Actual: A, B, C; Expected: A, C, B
        List<String> junit4TestCasesV2 = Arrays.asList(
                "com.luojl.demo.JUnit4DemoTest#TestA4",
                "com.luojl.demo.JUnit4DemoTest#TestC4",
                "com.luojl.demo.JUnit4DemoTest#TestB4");
        runMultipleTestMethods(toMethodSelectors(junit4TestCasesV2), listener);
        listener.getSummary().printTo(stdOutWriter);
    }
}
