package com.luojl.demo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class InheritedTest extends ParentTest {

    @Test
    void TestA() {}

    @Test
    void TestB() {}

    @Test
    void TestC() {}

    @Nested
    public class NestedTest {

        @Test
        void NestedTestA() {
            System.out.println("NestedTestA");
        }

        @Test
        void NestedTestB() {
            System.out.println("NestedTestB");
        }

        @Test
        void NestedTestC() {
            System.out.println("NestedTestC");
        }
    }
}