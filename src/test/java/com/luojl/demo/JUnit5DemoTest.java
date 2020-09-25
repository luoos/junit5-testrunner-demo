package com.luojl.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnit5DemoTest {

  @DisplayName("Simple Test")
  @Test
  void SimpleTest() {
    assertEquals("Hey there", "Hey there");
  }
}
