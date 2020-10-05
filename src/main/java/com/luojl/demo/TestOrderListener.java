package com.luojl.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

public class TestOrderListener implements TestExecutionListener {

    // Order matters!
    private List<SingleTestResult> testResults;

    public List<SingleTestResult> getResults() {
        return this.testResults;
    }

    /**
     * Return a list of full qualified method names in order.
     */
    public List<String> getTestOrder() {
        return this.testResults.stream()
                   .map(r -> r.getFullQualifiedMethodName())
                   .collect(Collectors.toList());
    }

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        this.testResults = new ArrayList<>();
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier,
                                  TestExecutionResult testExecutionResult) {
        if (testIdentifier.isTest()) {
            this.testResults.add(new SingleTestResult(testIdentifier, testExecutionResult));
        }
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testplan) {}

    @Override
    public void dynamicTestRegistered(TestIdentifier testIdentifier) {}

    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {}

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {}

    public static class SingleTestResult {
        private static final String junit5Regex = "\\[class:([\\w.]+).*\\[method:(\\w+)\\(";
        private static final Pattern junit5Pattern = Pattern.compile(junit5Regex);
        private static final String junit4Regex = "test:(\\w+)\\(([\\w.]+)\\)";
        private static final Pattern junit4Pattern = Pattern.compile(junit4Regex);

        private final TestIdentifier identifier;
        private final TestExecutionResult result;

        private SingleTestResult(TestIdentifier identifier, TestExecutionResult result) {
            this.identifier = identifier;
            this.result = result;
        }

        public TestIdentifier getTestIdentifier() {
            return this.identifier;
        }

        public TestExecutionResult getTestExecutionResult() {
            return this.result;
        }

        /**
         * Turn the uniqueId from identifier into full qualified method name.
         *
         * For JUnit 5:
         * uniqueId: [engine:junit-jupiter]/[class:com.luojl.demo.JUnit5DemoTest]/[method:TestC()]
         * full qualified name: com.luojl.demo.JUnit5DemoTest#TestC
         *
         * For JUnit 4:
         * uniqueId: [engine:junit-vintage]/[runner:com.luojl.demo.JUnit4DemoTest]/[test:TestA4(com.luojl.demo.JUnit4DemoTest)]
         * full qualified name: com.luojl.demo.JUnit4DemoTest#TestA4
         */
        public String getFullQualifiedMethodName() {
            String id = this.identifier.getUniqueId();
            Matcher matcher = junit5Pattern.matcher(id);
            if (matcher.find()) {
                // found JUnit 5 pattern
                return matcher.group(1) + "#" + matcher.group(2);
            }
            // fall back to JUnit 4
            matcher = junit4Pattern.matcher(id);
            matcher.find();
            return matcher.group(2) + "#" + matcher.group(1);
        }
    }
}
