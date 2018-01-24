LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := app

# Use WAVLib static library
#LOCAL_STATIC_LIBRARIES += wavlib_static

# Link with OpenSL ES
LOCAL_LDLIBS += -lOpenSLES

include $(BUILD_SHARED_LIBRARY)

# Import WAVLib library module
#$(call import-add-path,$(LOCAL_PATH))

LOCAL_SRC_FILES := com_study_eric_jni_JniTest.c
