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

    public static void runMultipleTests(List<MethodSelector> methods,
                                        TestExecutionListener listener) {
        // The execution order is same as order in 'methods' variable, unless:
        // 1. run JUnit4 tests. They will be sorted alphabetically
        // 2. JUnit5 tests with different classes interleaved,
        //    like ClassA#test1, ClassB#test1, ClassA#test2,
        //    the actual order is ClassA#test1, ClassA#test2, ClassB#test1
        // 3. JUnit5 tests with Order annotation.
        //    No matter how I shuffle the order, the actual order depends on
        //    the value of Order annotation.
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request().selectors(methods).build();
        Launcher launcher = LauncherFactory.create();
        launcher.execute(request, listener);
    }

    public static void runMultipleTestsSeparately(
            List<MethodSelector> methods,
            TestExecutionListener listener) {
        // The execution order is strictly equals to the order
        // in methods variable
        Launcher launcher = LauncherFactory.create();
        for (MethodSelector method : methods) {
            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                    .request().selectors(method).build();
            launcher.execute(request, listener);
        }
    }

    public static List<MethodSelector> toMethodSelectors(
            List<String> fullQualifiedMethodNames) {
        // The item in the list should be full qualified method name,
        // i.e., <package>.<class>#<method>
        return fullQualifiedMethodNames.stream()
                .map(s -> DiscoverySelectors.selectMethod(s))
                .collect(Collectors.toList());
    }
}
