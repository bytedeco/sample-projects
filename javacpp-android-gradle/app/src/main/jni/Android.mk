LOCAL_PATH := $(call my-dir)
define walk
$(wildcard $(1)) $(foreach e, $(wildcard $(1)/*), $(call walk, $(e)))
endef
include $(CLEAR_VARS)

ALLFILES = $(call walk, $(LOCAL_PATH))
#$(warning "the value of LOCAL_PATH is$(ALLFILES)")

LOCAL_MODULE    := jniMultiplyDemo
LOCAL_SRC_FILES := NativeLibrary.h\
                    jniMultiplyDemo.cpp


LOCAL_LDLIBS += -lm -llog -ldl -landroid -lc

include $(BUILD_SHARED_LIBRARY)
