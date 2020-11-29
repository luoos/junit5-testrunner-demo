package com.luojl.demo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.nio.file.Path;

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

        @ParameterizedTest
        @ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
        void ParameterizedTestA(String candidate) {
            System.out.println(candidate);
        }

        @Test
        void TestB(@TempDir Path workingDirectory) {
            System.out.println("Nested parameterized test");
        }

    }
}