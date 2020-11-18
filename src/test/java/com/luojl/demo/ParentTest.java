package com.luojl.demo;

import org.junit.jupiter.api.Test;

public class ParentTest extends GrandParentTest {

    @Test
    void ParentTestA() {}

    @Test
    void ParentTestB() {}

    @Test
    void ParentTestC() {}

    @Override
    @Test
    void GrandParentTestA() {}
}
