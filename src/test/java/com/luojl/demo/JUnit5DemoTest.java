package com.luojl.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@TestMethodOrder(OrderAnnotation.class)
public class JUnit5DemoTest {

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
}
