package com.luojl.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
    public void TestC() {
        System.out.println("[JUnit5] Running Test C");
    }
}
