package com.luojl.demo;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    void testGetAllMethods() {
        List<Method> methods = Utils.getAllMethods(InheritedTest.class);
        Set<String> methodNames = methods.stream().map(m -> m.toString())
                                         .collect(Collectors.toSet());
        List<String> expectedMethodNames = Arrays.asList(
            "void com.luojl.demo.InheritedTest.TestA()",
            "void com.luojl.demo.InheritedTest.TestB()",
            "void com.luojl.demo.InheritedTest.TestC()",
            "void com.luojl.demo.ParentTest.ParentTestA()",
            "void com.luojl.demo.ParentTest.ParentTestB()",
            "void com.luojl.demo.ParentTest.ParentTestC()",
            "void com.luojl.demo.ParentTest.GrandParentTestA()",
            "void com.luojl.demo.GrandParentTest.GrandParentTestB()",
            "void com.luojl.demo.GrandParentTest.GrandParentTestC()"
        );
        Assertions.assertTrue(methodNames.containsAll(expectedMethodNames));
    }

    @Test
    void testGetParametersOfMethod() {
        List<Method> methods = Arrays.asList(ParameterizedMethodClass.class.getDeclaredMethods());
        Assertions.assertTrue(methods.size() == 1);
        Assertions.assertEquals("java.lang.String,int,java.nio.file.Path",
                                Utils.getParameterListStr(methods.get(0)));
    }
}
