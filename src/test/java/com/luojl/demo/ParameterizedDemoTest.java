package com.luojl.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.nio.file.Path;

public class ParameterizedDemoTest {

    @ParameterizedTest
    @ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
    void TestA(String candidate) {
        System.out.println(candidate);
    }

    @ParameterizedTest
    @ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
    void Test1(String candidate) {
        System.out.println(candidate);
    }

    @Test
    void TestB(@TempDir Path workingDirectory) {
        System.out.println("ParameterizedDemoTest#testB");
    }

}
