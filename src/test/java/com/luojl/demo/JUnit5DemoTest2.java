package com.luojl.demo;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

@TestMethodOrder(OrderAnnotation.class)
public class JUnit5DemoTest2 {

    @Test
    public void TestA() {
        System.out.println("[JUnit5-2] Running Test A");
    }

    @Test
    public void TestB() {
        System.out.println("[JUnit5-2] Running Test B");
    }

    @Test
    public void TestC() {
        System.out.println("[JUnit5-2] Running Test C");
    }

    @Test
    @Order(2)
    public void testWithOrder2() {
        System.out.println("test with order 2");
    }

    @Test
    @Order(4)
    public void testWithOrder4() {
        System.out.println("test with order 4");
    }

    @Test
    @Order(6)
    public void testWithOrder6() {
        System.out.println("test with order 6");
    }
}
