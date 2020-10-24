package com.luojl.demo;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

public class TestOrderListener implements TestExecutionListener {

    // Order matters!
    private List<SingleTestResult> testResults;
    private List<SingleTestResult> skippedTests;

    public TestOrderListener() {
        reset();
    }

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

    public List<String> getSkippedTest() {
        return this.skippedTests.stream()
                   .map(r -> r.getFullQualifiedMethodName())
                   .collect(Collectors.toList());
    }

    public void reset() {
        this.testResults  = new ArrayList<>();
        this.skippedTests = new ArrayList<>();
    }

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {}

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
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
        this.skippedTests.add(new SingleTestResult(testIdentifier, reason));
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {}

    public static class SingleTestResult {

        private final TestIdentifier identifier;
        private TestExecutionResult result;
        private String reason;  // if skipped

        private SingleTestResult(TestIdentifier identifier, TestExecutionResult result) {
            this.identifier = identifier;
            this.result = result;
        }

        private SingleTestResult(TestIdentifier identifier, String reason) {
            this.identifier = identifier;
            this.reason = reason;
        }

        public String getReason() {
            return this.reason;
        }

        public TestIdentifier getTestIdentifier() {
            return this.identifier;
        }

        public TestExecutionResult getTestExecutionResult() {
            return this.result;
        }

        public String getFullQualifiedMethodName() {
            return Utils.toFullQualifiedName(this.identifier.getUniqueId());
        }
    }
}
