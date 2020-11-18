package com.luojl.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@TestMethodOrder(OrderAnnotation.class)
public class JUnit5DemoTest {

    private static int flakyStaticInt = 0;
    private static int flakyStaticInt2 = 0;
    private int flakyInt = 10;

    @BeforeAll
    static void initAll() {
        System.out.println("[JUnit5] BeforeAll");
    }

    @BeforeEach
    void init() {
        System.out.println("[JUnit5] BeforeEach. This: " + this);
    }

    @Test
    void SimpleTest() {
        assertEquals("Hey there", "Hey there");
    }

    @Test
    void TestA() {
        System.out.println("[JUnit5] Running Test A");
    }

    @Test
    void TestB() {
        System.out.println("[JUnit5] Running Test B");
    }

    @Test
    @DisplayName("CC")
    void TestC() {
        System.out.println("[JUnit5] Running Test C");
    }

    @Test
    @Disabled
    void disabledTest() {
        System.out.println("Should not run");
    }

    @Test
    @Order(5)
    void testWithOrder5() {
        System.out.println("test with order 5");
    }

    @Test
    @Order(3)
    void testWithOrder3() {
        System.out.println("test with order 3");
    }

    @Test
    @Order(10)
    void testWithOrder10() {
        System.out.println("test with order 10");
    }

    @Test
    void flakytest1() {
        assertEquals(0, this.flakyStaticInt);
        this.flakyStaticInt += 1;
    }

    @Test
    void flakytest2() {
        // flakytest1 should run first, otherwise this will fail
        assertEquals(1, this.flakyStaticInt);
    }

    @Test
    void testIncreaseVal() {
        flakyStaticInt2 += 1;
        this.flakyInt += 1;
        System.out.println("increase flakyInt to " + this.flakyInt);
        System.out.println("increase flakyStaticInt2 to " + this.flakyStaticInt2);
    }

    @Test
    void testCheckVal() {
        System.out.println("flakyInt: " + this.flakyInt);
        System.out.println("flakyStaticInt2: " + flakyStaticInt2);
    }
}
