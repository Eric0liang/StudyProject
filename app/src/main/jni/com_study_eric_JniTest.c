#include "JniTest.h"

JNIEXPORT jstring JNICALL Java_com_study_eric_jni_JniTest_getJniString
        (JNIEnv *env, jobject object) {
    return (*env)->NewStringUTF(env, "Hello from JNI !");
}

JNIEXPORT jstring JNICALL Java_com_study_eric_jni_JniTest_getStringFromJavaField
        (JNIEnv *env, jobject object, jobject jobject1) {
    jclass clazz = (*env)->GetObjectClass(env, jobject1); // 先获取到对象的类
    jfieldID jfieldID = (*env)->GetFieldID(env, clazz, "instanceField", "Ljava/lang/String;"); // 获取到该类的对象"instanceField"的ID
    return (*env)->GetObjectField(env, jobject1, jfieldID);
}

JNIEXPORT jstring JNICALL Java_com_study_eric_jni_JniTest_getStringFromJavaInstance
        (JNIEnv *env, jobject object, jobject jobject1) {
    jclass clazz = (*env)->GetObjectClass(env, jobject1);
    jmethodID jmethodID = (*env)->GetMethodID(env, clazz, "instanceMethod", "()Ljava/lang/String;");
    return (*env)->CallObjectMethod(env, jobject1, jmethodID);
}

JNIEXPORT jstring JNICALL Java_com_study_eric_jni_JniTest_getStringFromJavaThrowing
        (JNIEnv *env, jobject object, jobject jobject1) {
    jclass clazz = (*env)->GetObjectClass(env, jobject1);
    jmethodID jmethodID = (*env)->GetMethodID(env, clazz, "throwingMethod", "()V");
    (*env)->CallNonvirtualVoidMethod(env, jobject1, clazz, jmethodID);
    jthrowable ex;
    ex = (*env)->ExceptionOccurred(env);
    if (ex != 0) {
        (*env)->ExceptionClear(env);
        return (*env)->NewStringUTF(env, "null pointer");
    } else {
        return (*env)->NewStringUTF(env, "success");
    }
}

JNIEXPORT void JNICALL Java_com_study_eric_jni_JniTest_getFromJniThrowing
        (JNIEnv *env, jobject object) {
    jclass clazz = (*env)->FindClass(env, "java/lang/NullPointerException");
    if (clazz != 0) {
        (*env)->ThrowNew(env, clazz, "Exception message.");
    }
}
