package com.study.eric.jni;

/**
 * JNI调用Java
 * Created by liangjiangli on 2017/5/26.
 */

public class JavaClass {
    private String instanceField = "Instance Field";
    private static String staticField = "Static Field";

    /**
     * 实例方法
     */
    private String instanceMethod() {
        return "Instance Method";
    }
    /**
     * 静态方法
     */
    private static String staticMethod() {
        return "Static Method";
    }

    /**
     * 抛出异常方法
     */
    private void throwingMethod() throws NullPointerException {
        throw  new NullPointerException("Null pointer");
    }
}
