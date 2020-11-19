package com.luojl.demo;

import static com.luojl.demo.TestRunner.toMethodSelectors;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestRunnerTest {

    @Test
    public void runJUnit5TestsRound1() {
        TestOrderListener listener = new TestOrderListener();
        List<String> junit5TestCases = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#TestB",
                "com.luojl.demo.JUnit5DemoTest#TestA");
        TestRunner.runMultipleTests(toMethodSelectors(junit5TestCases),
                                    listener);
        Assertions.assertIterableEquals(junit5TestCases,
                                        listener.getTestOrder());
    }

    @Test
    public void runJUnit5TestsRound2() {
        TestOrderListener listener = new TestOrderListener();
        List<String> junit5TestCases = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#TestA",
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#TestB");
        TestRunner.runMultipleTests(toMethodSelectors(junit5TestCases),
                                    listener);
        Assertions.assertIterableEquals(junit5TestCases,
                                        listener.getTestOrder());
    }

    @Test
    public void runJUnit5TestsInterleaved() {
        // The actual order is unexpected, tests in same class will be
        // grouped together.
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
        TestRunner.runMultipleTests(toMethodSelectors(junit5TestFromTwoClasses),
                                    listener);
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
        TestRunner.runMultipleTests(toMethodSelectors(junit5TestCases),
                                    listener);
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
        TestRunner.runMultipleTests(toMethodSelectors(junit5TestCases),
                                    listener);
        Assertions.assertIterableEquals(actualOrder, listener.getTestOrder());
    }

    @Test
    public void runTestsWithOrderAnnotationMultipleClasses() {
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
        TestRunner.runMultipleTests(toMethodSelectors(junit5TestCases),
                                    listener);
        Assertions.assertIterableEquals(actualOrder, listener.getTestOrder());
    }

    @Test
    public void runMixedTestsSeparately() {
        TestOrderListener listener = new TestOrderListener();
        List<String> tests = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder6",
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder10",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder3",
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder2");
        TestRunner.runMultipleTestsSeparately(toMethodSelectors(tests),
                                              listener);
        Assertions.assertIterableEquals(tests, listener.getTestOrder());
    }

    @Test
    @Disabled
    public void comparePerformance() {
        // Run all in a single TestPlan vs separate TestPlan
        List<String> tests = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#SimpleTest",
                "com.luojl.demo.JUnit5DemoTest#TestA",
                "com.luojl.demo.JUnit5DemoTest#TestB",
                "com.luojl.demo.JUnit5DemoTest#TestC",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder5",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder3",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder10",
                "com.luojl.demo.JUnit5DemoTest#testWithOrder10",
                "com.luojl.demo.JUnit5DemoTest2#TestA",
                "com.luojl.demo.JUnit5DemoTest2#TestB",
                "com.luojl.demo.JUnit5DemoTest2#TestC",
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder2",
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder4",
                "com.luojl.demo.JUnit5DemoTest2#testWithOrder6");

        // warm up
        TestOrderListener listener = new TestOrderListener();
        TestRunner.runMultipleTestsSeparately(toMethodSelectors(tests),
                                              listener);
        listener.reset();
        TestRunner.runMultipleTests(toMethodSelectors(tests),
                                    listener);

        // run separately - 82ms
        long start = System.currentTimeMillis();
        listener.reset();
        TestRunner.runMultipleTestsSeparately(toMethodSelectors(tests),
                                              listener);
        long end = System.currentTimeMillis();
        long separateTime = end - start;

        // run together - 33 ms
        start = System.currentTimeMillis();
        listener.reset();
        TestRunner.runMultipleTests(toMethodSelectors(tests),
                                    listener);
        end = System.currentTimeMillis();
        long togetherTime = end - start;

        System.out.println("separate testplan, time: " + separateTime);
        System.out.println("single testplan, time: " + togetherTime);
    }

    @Test
    public void testBeforeAllAndEachRunSeparately() {
        List<String> tests = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#testIncreaseVal",
                "com.luojl.demo.JUnit5DemoTest#testCheckVal");
        TestOrderListener listener = new TestOrderListener();
        TestRunner.runMultipleTestsSeparately(toMethodSelectors(tests),
                                              listener);
        Assertions.assertIterableEquals(tests, listener.getTestOrder());

        /**
         * Output: (run separately)
         *      [JUnit5] BeforeAll
         *      [JUnit5] BeforeEach. This: com.luojl.demo.JUnit5DemoTest@2fd1433e
         *      increase flakyInt to 11
         *      increase flakyStaticInt2 to 1
         *      [JUnit5] BeforeAll
         *      [JUnit5] BeforeEach. This: com.luojl.demo.JUnit5DemoTest@383bfa16
         *      flakyInt: 10
         *      flakyStaticInt2: 1
         *
         * BeforeAll will be invoked for each test when running separately
         * 2 tests, 2 BeforeAll, 2 BeforeEach
         *
         * Use the same static variable
         * non-static vaiable will be reset for each test
         */
    }

    @Test
    public void testBeforeAllAndEachRunTogether() {
        List<String> tests = Arrays.asList(
                "com.luojl.demo.JUnit5DemoTest#testIncreaseVal",
                "com.luojl.demo.JUnit5DemoTest#testCheckVal");
        TestOrderListener listener = new TestOrderListener();
        TestRunner.runMultipleTests(toMethodSelectors(tests),
                                    listener);
        Assertions.assertIterableEquals(tests, listener.getTestOrder());

        /**
         * Output: (run together)
         *      [JUnit5] BeforeAll
         *      [JUnit5] BeforeEach. This: com.luojl.demo.JUnit5DemoTest@793be5ca
         *      increase flakyInt to 11
         *      increase flakyStaticInt2 to 1
         *      [JUnit5] BeforeEach. This: com.luojl.demo.JUnit5DemoTest@78123e82
         *      flakyInt: 10
         *      flakyStaticInt2: 1
         *
         * BeforeAll just runs once
         * 2 tests, 1 BeforeAll, 2 BeforeEach
         *
         * Use the same static variable
         * non-static vaiable will be reset for each test
         */
    }

    @Test
    void testRunNestedTests() {
        List<String> tests = Arrays.asList(
                "com.luojl.demo.InheritedTest$NestedTest#NestedTestB",
                "com.luojl.demo.InheritedTest$NestedTest#NestedTestA",
                "com.luojl.demo.InheritedTest$NestedTest#NestedTestC");
        TestOrderListener listener = new TestOrderListener();
        TestRunner.runMultipleTests(toMethodSelectors(tests),
                                    listener);
        Assertions.assertIterableEquals(tests, listener.getTestOrder());
    }

}
