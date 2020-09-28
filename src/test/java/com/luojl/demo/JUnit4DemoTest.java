package com.luojl.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JUnit4DemoTest {

    @Test
    public void junit4SimpleTest() {
        assertEquals(1, 1);
    }

    @Test
    public void TestA4() {
        System.out.println("[JUnit4] running Test A");
    }

    @Test
    public void TestB4() {
        System.out.println("[JUnit4] running Test B");
    }
}
