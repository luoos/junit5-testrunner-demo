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

        // First round: TestA runs first, then TestB
        List<String> testMethodStrs = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestA",
                "com.luojl.demo.JUnit5DemoTest#TestB");
        runMultipleTestMethods(toMethodSelectors(testMethodStrs), listener);
        listener.getSummary().printTo(stdOutWriter);

        // Second round: TestB runs first, then TestA
        List<String> reversedTestMethodStrs = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestB",
                "com.luojl.demo.JUnit5DemoTest#TestA");
        runMultipleTestMethods(toMethodSelectors(reversedTestMethodStrs), listener);
        listener.getSummary().printTo(stdOutWriter);
    }
}
