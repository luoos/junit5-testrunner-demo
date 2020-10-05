package com.luojl.demo;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.discovery.MethodSelector;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

public class TestRunner {

    public static void runMultipleTestMethodStrs(List<String> fullQualifiedMethodNames,
                                                 TestExecutionListener listener) {
        runMultipleTestMethods(toMethodSelectors(fullQualifiedMethodNames), listener);
    }

    public static void runMultipleTestMethods(List<MethodSelector> methods,
                                              TestExecutionListener listener) {
        // The execution order is same as order in 'methods' variable.
        // So changing the order in 'methods' variable can change the test order
        // (the above statement is true for JUnit 5 test cases, but not for JUnit 4)
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(methods).build();
        Launcher launcher = LauncherFactory.create();
        launcher.execute(request, listener);
    }

    private static List<MethodSelector> toMethodSelectors(List<String> fullQualifiedMethodNames) {
        // The item in the list should be full qualified method name,
        // i.e., <package>.<class>#<method>
        return fullQualifiedMethodNames.stream().map(s -> DiscoverySelectors.selectMethod(s))
                .collect(Collectors.toList());
    }
}
