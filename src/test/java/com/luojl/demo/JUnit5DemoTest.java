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
    public void SimpleTest() {
        assertEquals("Hey there", "Hey there");
    }

    @Test
    public void TestA() {
        System.out.println("[JUnit5] Running Test A");
    }

    @Test
    public void TestB() {
        System.out.println("[JUnit5] Running Test B");
    }

    @Test
    @DisplayName("CC")
    public void TestC() {
        System.out.println("[JUnit5] Running Test C");
    }

    @Test
    @Disabled
    public void disabledTest() {
        System.out.println("Should not run");
    }

    @Test
    @Order(5)
    public void testWithOrder5() {
        System.out.println("test with order 5");
    }

    @Test
    @Order(3)
    public void testWithOrder3() {
        System.out.println("test with order 3");
    }

    @Test
    @Order(10)
    public void testWithOrder10() {
        System.out.println("test with order 10");
    }

    @Test
    public void flakytest1() {
        assertEquals(0, this.flakyStaticInt);
        this.flakyStaticInt += 1;
    }

    @Test
    public void flakytest2() {
        // flakytest1 should run first, otherwise this will fail
        assertEquals(1, this.flakyStaticInt);
    }

    @Test
    public void testIncreaseVal() {
        flakyStaticInt2 += 1;
        this.flakyInt += 1;
        System.out.println("increase flakyInt to " + this.flakyInt);
        System.out.println("increase flakyStaticInt2 to " + this.flakyStaticInt2);
    }

    @Test
    public void testCheckVal() {
        System.out.println("flakyInt: " + this.flakyInt);
        System.out.println("flakyStaticInt2: " + flakyStaticInt2);
    }
}
