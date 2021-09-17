#include <jni.h>
#include <string>
#include <android/log.h>


extern "C"{
extern int test();
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_dongnao_lsn9_1test_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    __android_log_print(ANDROID_LOG_ERROR,"jni","libtest.so 里面的 test 方法:%d",test());
    return env->NewStringUTF(hello.c_str());
}
