APP_PLATFORM := android-8
APP_ABI :=  armeabi-v7a  #armeabi x86  #armeabi-v7a  armeabi #armeabi-v7a #armeabi  #arm64-v8a x86
APP_STL := gnustl_static
APP_CPPFLAGS += -std=c++11 -frtti -fexceptions
NDK_TOOLCHAIN_VERSION := 4.9
#NDK_TOOLCHAIN_VERSION := clang
APP_OPTIM := release