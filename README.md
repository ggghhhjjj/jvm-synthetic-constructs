# About

Project demonstrates JVM synthetic constructs.

# What is JVM synthetic construct

Based on [JLS 13.1.11] a construct emitted by a Java compiler must be marked as synthetic if it does not correspond to a construct declared explicitly or implicitly in source code, unless the emitted construct is a class initialization method (JVMS §2.9).

# How does nested class get access to private parent fields

Let's consider an example

```java
public class Parent {
    private int i;

    private class Nested {
        int getI() {
            return i;
        }
    }
}
```

Inside `Nested` class Java compiler will create:

```java
private class Nested {

    final synthetic Parent this$0;

    private Nested (this, Parent p) {
        this.this$0 = p;
    }

    int getI() {
        return this.this$0.i;
    }
}
```

Constructor's first argument is always reference to the created object.
Based on [JLS 2.6.1] The Java Virtual Machine uses local variables to pass parameters on method invocation. On class method invocation, any parameters are passed in consecutive local variables starting from local variable 0. On instance method invocation, local variable 0 is always used to pass a reference to the object on which the instance method is being invoked (this in the Java programming language). Any parameters are subsequently passed in consecutive local variables starting from local variable 1.

Constructor's second argument is reference to the enclosed `Parent` object. The reference is assigned to the hidden (synthetic) field `this$0`

[JLS 13.1.11]: https://docs.oracle.com/javase/specs/jls/se8/html/jls-13.html#jls-13.1-120-K
[JLS 2.6.1]: https://docs.oracle.com/javase/specs/jvms/se16/html/jvms-2.html#jvms-2.6.1-300