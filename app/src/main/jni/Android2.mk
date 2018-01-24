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

#动态链接日志库
LOCAL_LDLIBS :=  -L$(SYSROOT)/usr/lib -llog
#定义日志标签
LOG_TAG := \"hello-jni\"

#定义默认日志等级
ifeq ($(APP_OPTIM), release)
    LOG_LEVEL := LOG_LEVEL_SILENT
else
    LOG_LEVEL := LOG_LEVEL_VERBOSE
endif

#追加编译标记
LOCAL_CFLAGS += -DLOG_TAG=$(LOG_TAG)
LOCAL_CFLAGS += -DLOG_LEVEL=$(LOG_LEVEL)

include $(BUILD_SHARED_LIBRARY)
