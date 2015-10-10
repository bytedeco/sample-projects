javacpp-mvn-simple-demo
=======================

This is a simple, stripped-down demonstration of using JavaCPP 1.0 (and the associated maven plugin) to compile a project containing both Java and C++ code.

To compile and unit test: 

```
mvn clean install
```

And to execute:

```
mvn "-Dexec.args=-classpath %classpath somepackage.MultiplyDemo" -Dexec.executable=java exec:exec
```

By uncommenting the maven-assembly-plugin you can compile to a jar with the dependencies (i.e. the javacpp jar file) bundled inside - then you can run the demo with ```java -jar whatever.jar```
