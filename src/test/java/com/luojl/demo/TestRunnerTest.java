package com.luojl.demo;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestRunnerTest {

    @Test
    public void runJUnit5TestsRound1() {
        TestOrderListener listener = new TestOrderListener();
        List<String> junit5TestCases = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#TestB",
                "com.luojl.demo.JUnit5DemoTest#TestA");
        TestRunner.runMultipleTestMethodStrs(junit5TestCases, listener);
        Assertions.assertIterableEquals(junit5TestCases, listener.getTestOrder());
    }

    @Test
    public void runJUnit5TestsRound2() {
        TestOrderListener listener = new TestOrderListener();
        List<String> junit5TestCases = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestA",
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#TestB");
        TestRunner.runMultipleTestMethodStrs(junit5TestCases, listener);
        Assertions.assertIterableEquals(junit5TestCases, listener.getTestOrder());
    }

    @Test
    public void runJUnit5TestsInterleaved() {
        // The actual order is unexpected, tests in same class will be grouped together
        // But order in the same class is preserved
        TestOrderListener listener = new TestOrderListener();
        List<String> junit5TestFromTwoClasses = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestB",
                "com.luojl.demo.JUnit5DemoTest2#TestC",
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest2#TestA",
                "com.luojl.demo.JUnit5DemoTest#TestA");
        List<String> actualOrder = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestB",
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#TestA",
                "com.luojl.demo.JUnit5DemoTest2#TestC",
                "com.luojl.demo.JUnit5DemoTest2#TestA");
        TestRunner.runMultipleTestMethodStrs(junit5TestFromTwoClasses, listener);
        Assertions.assertIterableEquals(actualOrder, listener.getTestOrder());
    }

    @Test
    public void runJUnit4TestsRound1() {
        // The actual order is unexpected, tests are sorted alphabetically
        TestOrderListener listener = new TestOrderListener();
        List<String> junit4TestCases = Arrays.asList(
                "com.luojl.demo.JUnit4DemoTest#TestC4",
                "com.luojl.demo.JUnit4DemoTest#TestB4",
                "com.luojl.demo.JUnit4DemoTest#TestA4");
        List<String> actualOrder = Arrays.asList(
                "com.luojl.demo.JUnit4DemoTest#TestA4",
                "com.luojl.demo.JUnit4DemoTest#TestB4",
                "com.luojl.demo.JUnit4DemoTest#TestC4");
        TestRunner.runMultipleTestMethodStrs(junit4TestCases, listener);
        Assertions.assertIterableEquals(actualOrder, listener.getTestOrder());
    }

    @Test
    public void runDisableTests() {
        // disabled test will be skipped
        TestOrderListener listener = new TestOrderListener();
        List<String> junit5TestCases = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#disabledTest",
                "com.luojl.demo.JUnit5DemoTest#TestB",
                "com.luojl.demo.JUnit5DemoTest#TestA");
        List<String> actualOrder = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#TestB",
                "com.luojl.demo.JUnit5DemoTest#TestA");
        TestRunner.runMultipleTestMethodStrs(junit5TestCases, listener);
        Assertions.assertIterableEquals(actualOrder, listener.getTestOrder());
        Assertions.assertIterableEquals(
                Arrays.asList("com.luojl.demo.JUnit5DemoTest#disabledTest"),
                listener.getSkippedTest());
    }

    @Test
    public void runTestsWithOrderAnnotation() {
        // The actual order is dominated by Order annotation
        // test with smaller Order value runs first
        TestOrderListener listener = new TestOrderListener();
        List<String> junit5TestCases = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#testWithOrder5",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder3",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder10");
        List<String> actualOrder = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#testWithOrder3",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder5",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder10");
        TestRunner.runMultipleTestMethodStrs(junit5TestCases, listener);
        Assertions.assertIterableEquals(actualOrder, listener.getTestOrder());
    }

    @Test
    public void runTestsWithOrderAnnotationMultipleClasses() {
        System.out.println("runTestsWithOrderAnnotationMultipleClasses");
        // The actual order is dominated by Order annotation
        // Tests in same class will be grouped together.
        // Test with smaller Order value runs first
        TestOrderListener listener = new TestOrderListener();
        List<String> junit5TestCases = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#testWithOrder5",
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder2",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder3",
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder6",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder10",
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder4");
        List<String> actualOrder = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#testWithOrder3",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder5",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder10",
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder2",
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder4",
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder6");
        TestRunner.runMultipleTestMethodStrs(junit5TestCases, listener);
        Assertions.assertIterableEquals(actualOrder, listener.getTestOrder());
    }
}
