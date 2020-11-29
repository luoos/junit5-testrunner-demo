package com.luojl.demo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

public class Utils {
    private static final String junit5NestParameterizedRegex =
            "\\[class:([\\w.]+).*\\[nested-class:([\\w.]+).*\\[test-template:([\\w().]+).*\\[test-template-invocation:(#\\d)";
    private static final Pattern junit5NestParameterizedPattern =
            Pattern.compile(junit5NestParameterizedRegex);
    private static final String junit5ParameterizedRegex =
            "\\[class:([\\w.]+).*\\[test-template:([\\w().]+).*\\[test-template-invocation:(#\\d)";
    private static final Pattern junit5ParameterizedPattern =
            Pattern.compile(junit5ParameterizedRegex);
    private static final String junit5NestedRegex = "\\[class:([\\w.]+).*\\[nested-class:([\\w.]+).*\\[method:([\\w().]+)";
    private static final Pattern junit5NestedPattern = Pattern.compile(junit5NestedRegex);
    private static final String junit5Regex = "\\[class:([\\w.]+).*\\[method:([\\w().]+)";
    private static final Pattern junit5Pattern = Pattern.compile(junit5Regex);
    private static final String junit4Regex = "\\[test:(\\w+)\\(([\\w.]+)\\)";
    private static final Pattern junit4Pattern = Pattern.compile(junit4Regex);

    /**
     * Turn the uniqueId from identifier into full qualified method name.
     *
     * For JUnit 5 nested parameterized tests:
     * uniqueId: [engine:junit-jupiter]/[class:com.luojl.demo.InheritedTest]/[nested-class:NestedTest]/[test-template:ParameterizedTestA(java.lang.String)]/[test-template-invocation:#2]
     * fully qualified name: com.luojl.demo.InheritedTest$NestedTest#ParameterizedTestA(java.lang.String)#2
     *
     * For JUnit 5 parameterized test:
     * uniqueId: [engine:junit-jupiter]/[class:com.luojl.demo.ParameterizedDemoTest]/[test-template:test1(java.lang.String)]/[test-template-invocation:#1]
     * fully qualified name: com.luojl.demo.ParameterizedDemoTest#test1(java.lang.String)#1
     *
     * For JUnit 5 nested test:
     * uniqueId: [engine:junit-jupiter]/[class:com.luojl.demo.InheritedTest]/[nested-class:NestedTest]/[method:NestedTestB()]
     * fully qualified name: com.luojl.demo.InheritedTest$NestedTest#NestedTestB
     *
     * For JUnit 5:
     * uniqueId: [engine:junit-jupiter]/[class:com.luojl.demo.JUnit5DemoTest]/[method:TestC()]
     * fully qualified name: com.luojl.demo.JUnit5DemoTest#TestC
     *
     * For JUnit 4:
     * uniqueId: [engine:junit-vintage]/[runner:com.luojl.demo.JUnit4DemoTest]/[test:TestA4(com.luojl.demo.JUnit4DemoTest)]
     * fully qualified name: com.luojl.demo.JUnit4DemoTest#TestA4
     */
    public static String toFullyQualifiedName(String identifierUniqueId) {
        Matcher matcher = junit5NestParameterizedPattern.matcher(identifierUniqueId);
        if (matcher.find()) {
            return matcher.group(1) + "$" + matcher.group(2) + "#" + matcher.group(3)
                   + matcher.group(4);
        }
        matcher = junit5ParameterizedPattern.matcher(identifierUniqueId);
        if (matcher.find()) {
            return matcher.group(1) + "#" + matcher.group(2) + matcher.group(3);
        }
        matcher = junit5NestedPattern.matcher(identifierUniqueId);
        if (matcher.find()) {
            return matcher.group(1) + "$" + matcher.group(2) + "#" + matcher.group(3);
        }
        matcher = junit5Pattern.matcher(identifierUniqueId);
        if (matcher.find()) {
            // found JUnit 5 pattern
            return matcher.group(1) + "#" + matcher.group(2);
        }
        // fall back to JUnit 4
        matcher = junit4Pattern.matcher(identifierUniqueId);
        matcher.find();
        return matcher.group(2) + "#" + matcher.group(1);
    }

    public static List<Method> getAllMethods(Class clz) {
        List<Method> methods = new ArrayList<>();
        Set<String> methodNameSet = new HashSet<>();
        for (; clz != null; clz = clz.getSuperclass()) {
            for (Method m : clz.getDeclaredMethods()) {
                if (methodNameSet.contains(m.getName())) {
                    // exclude override method
                    continue;
                }
                methods.add(m);
                methodNameSet.add(m.getName());
            }
        }
        return methods;
    }
}
