#include <jni.h>
#include <pthread.h>
#include <string>
#include <android/log.h>


#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,"JNI",__VA_ARGS__);

JavaVM *_vm;

void dynamicTest(){

    LOGE("JNI dynamicTest");
}

jint dynamicTest2(JNIEnv *env, jobject instance,jint i){
    LOGE("JNI dynamicTest2:%d",i);
    return 100;
}

static const JNINativeMethod method[] = {
        {"dynamicJavaTest","()V",(void*)dynamicTest},
        {"dynamicJavaTest2","(I)I",(int*)dynamicTest2},
};
static const char *mClassName = "com/dongnao/lsn8_example/MainActivity";

int JNI_OnLoad(JavaVM *vm,void *re){
    __android_log_print(ANDROID_LOG_ERROR,"JNI","JNI_Onload");
    //
    _vm = vm;
    // 获得JNIEnv
    JNIEnv *env = 0;
    // 小于0 失败 ，等于0 成功
    int r = vm->GetEnv((void**)&env,JNI_VERSION_1_2);
    if (r != JNI_OK){
        return -1;
    }
    //获得 class对象
    jclass jcls =  env->FindClass(mClassName);
    //注册
    env->RegisterNatives(jcls,method, sizeof(method)/ sizeof(JNINativeMethod));
    return JNI_VERSION_1_2;
}

struct Context{
    jobject instance;
};

pthread_mutex_t mutex;
pthread_cond_t cond;

void* threadTask(void* args){
    // native线程 附加 到 Java 虚拟机
    JNIEnv *env;
    jint i = _vm->AttachCurrentThread(&env,0);
    if (i != JNI_OK){
        return 0;
    }

    //1、下载完成
    //2、下载失败
    Context *context = static_cast<Context *>(args);
    //获得MainActivity的class对象
    jclass cls = env->GetObjectClass(context->instance);
    // 反射获得方法
    jmethodID  updateUI = env->GetMethodID(cls,"updateUI","()V");
    env->CallVoidMethod(context->instance,updateUI);
    //分离
    _vm->DetachCurrentThread();
    LOGE("子线程执行完了，准备通知主线程释放资源")
    pthread_cond_signal(&cond);
    return 0;
}

extern  "C"
JNIEXPORT void JNICALL
Java_com_dongnao_lsn8_1example_MainActivity_testThread(JNIEnv *env, jobject instance) {
    pthread_mutex_init(&mutex, nullptr);
    pthread_cond_init(&cond, nullptr);
    pthread_t pid;
    //启动线程 下载视频
    Context *context = new Context;
    context->instance = env->NewGlobalRef(instance);
    pthread_create(&pid,0,threadTask,context);
    pthread_cond_wait(&cond, &mutex);
    LOGE("收到子线程执行完的消息，开始释放资源")
    // 等子线程执行完了再删除掉资源
    env->DeleteGlobalRef(context->instance);
    delete(context);
    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&cond);
}



