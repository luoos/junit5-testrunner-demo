# JUnit 5 Test Runner Demo

This is a demo project that shows how to use JUnit 5 to run test cases programmatically.

The [TestRunner](src/main/java/com/luojl/demo/TestRunner.java) runs two test cases in [JUnit5DemoTest.java](src/test/java/com/luojl/demo/JUnit5DemoTest.java) with two different orders.

Sample output:

```shell
$ mvn compile exec:java -Dexec.mainClass="com.luojl.demo.TestRunner" -Dexec.classpathScope="test"

(...)

Running Test A
Running Test B

Test run finished after 84 ms
[         2 containers found      ]
[         0 containers skipped    ]
[         2 containers started    ]
[         0 containers aborted    ]
[         2 containers successful ]
[         0 containers failed     ]
[         2 tests found           ]
[         0 tests skipped         ]
[         2 tests started         ]
[         0 tests aborted         ]
[         2 tests successful      ]
[         0 tests failed          ]

Running Test B
Running Test A

Test run finished after 3 ms
[         2 containers found      ]
[         0 containers skipped    ]
[         2 containers started    ]
[         0 containers aborted    ]
[         2 containers successful ]
[         0 containers failed     ]
[         2 tests found           ]
[         0 tests skipped         ]
[         2 tests started         ]
[         0 tests aborted         ]

(...)
```

## Useful References

1. [LauncherDiscoveryRequestBuilder](https://junit.org/junit5/docs/5.0.0/api/org/junit/platform/launcher/core/LauncherDiscoveryRequestBuilder.html)
2. [Running JUnit Tests Programmatically, from a Java Application](https://www.baeldung.com/junit-tests-run-programmatically-from-java)
3. [Changing the classpath scope when running Java programs](https://www.mojohaus.org/exec-maven-plugin/examples/example-exec-or-java-change-classpath-scope.html)
