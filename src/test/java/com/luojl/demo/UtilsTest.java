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

    @Test
    void testMatchNestedClassOrder() {
        // A B C
        String fakeIdentifierId = "[class:com.package.OuterClass]/[nested-class:ClassA]/[nested-class:ClassB]/[nested-class:ClassC]/[method:method()]";
        Assertions.assertEquals("com.package.OuterClass$ClassA$ClassB$ClassC#method()",
                                Utils.toFullyQualifiedName(fakeIdentifierId));

        // B A C
        fakeIdentifierId = "[class:com.package.OuterClass]/[nested-class:ClassB]/[nested-class:ClassA]/[nested-class:ClassC]/[method:method()]";
        Assertions.assertEquals("com.package.OuterClass$ClassB$ClassA$ClassC#method()",
                                Utils.toFullyQualifiedName(fakeIdentifierId));

        // C B A
        fakeIdentifierId = "[class:com.package.OuterClass]/[nested-class:ClassC]/[nested-class:ClassB]/[nested-class:ClassA]/[method:method()]";
        Assertions.assertEquals("com.package.OuterClass$ClassC$ClassB$ClassA#method()",
                                Utils.toFullyQualifiedName(fakeIdentifierId));
    }
}
