package com.study.eric.jni;

/**
 * JNI hello world!
 * Created by liangjiangli on 2017/5/25.
 */

public class JniTest {
    static {
        System.loadLibrary("app"); //jni模块加载名称
    }
    public native String getJniString(); //该方法是红色的，暂时不用理会

    public native String getStringFromJavaField(JavaClass j); //JNI调用java属性

    public native String getStringFromJavaInstance(JavaClass j); //JNI调用java实例域

    public native String getStringFromJavaThrowing(JavaClass j); //JNI调用java异常

    public native void getFromJniThrowing(); //JNI抛异

}
