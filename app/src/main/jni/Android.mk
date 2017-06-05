LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := app
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/daredos/Android/Study/app/src/main/jni/com_study_eric_JniTest.c \

LOCAL_C_INCLUDES += /Users/daredos/Android/Study/app/src/main/jni
LOCAL_C_INCLUDES += /Users/daredos/Android/Study/app/src/debug/jni

SWIG_PACKAGE := com.study.eric.swig
SWIG_INTERFACES := Unix.i
SWIG_TYPE := c
include $(LOCAL_PATH)/swig-generate.mk

include $(BUILD_SHARED_LIBRARY)
