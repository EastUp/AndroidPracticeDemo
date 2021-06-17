#include <jni.h>
#include <string>
#include <android/log.h>

//  __VA_ARGS__ 代表... 可变参数
#define  LOGE(...) __android_log_print(ANDROID_LOG_ERROR,"JNI",__VA_ARGS__);

extern "C"
JNIEXPORT jint JNICALL
Java_com_dongnao_jnitest_MainActivity_test(JNIEnv *env, jobject instance, jintArray i_,
                                           jobjectArray j_) {
    //指向数组首元素地址
    // 第二个参数：指针：指向内存地址
    // 在这个地址存数据
    // true：是拷贝的一个新数据 (新申请内存)
    // false: 就是使用的java的数组 (地址)
    jint *i = env->GetIntArrayElements(i_, NULL);
    //C  调用： (*env)->GetIntArrayElements(env,i_, NULL);
    //获取数组长度
    int32_t length = env->GetArrayLength(i_);
    for (int k = 0; k < length; ++k) {
        LOGE("获取java的参数:%d",*(i+k));
        //修改值
        *(i+k) = 100;
    }
    // 参数3：mode
    // 0:  刷新java数组 并 释放c/c++数组
    // 1 = JNI_COMMIT:
    //      只刷新java数组
    // 2 = JNI_ABORT
    //      只释放
    env->ReleaseIntArrayElements(i_, i, 0);
    //  (*env)->ReleaseIntArrayElements(env,i_, i, 0);


    jint strlength = env->GetArrayLength(j_);
    for (int i = 0; i < strlength; ++i) {
        jstring str = static_cast<jstring>(env->GetObjectArrayElement(j_, i));
        //转成可操作的c/c++字符串
        const char* s = env->GetStringUTFChars(str,0);
        LOGE("获取java的参数:%s",s);
        env->ReleaseStringUTFChars(str,s);
    }
    return 100;
}


extern "C"
JNIEXPORT void JNICALL
Java_com_dongnao_jnitest_MainActivity_passObject(JNIEnv *env, jobject instance, jobject bean,
                                                 jstring str_) {
    const char *str = env->GetStringUTFChars(str_, 0);

    //反射调用java方法
    //1、获取java对应的class对象
    jclass beanCls = env->GetObjectClass(bean);
    //2、找到要调用的方法
    // 参数3： 签名
    //get方法
    jmethodID  getI = env->GetMethodID(beanCls,"getI","()I");
    //3、调用
    jint i = env->CallIntMethod(bean,getI);
    LOGE("C++ 调用Java getI方法:%d",i);

    //set 方法
    jmethodID  setI = env->GetMethodID(beanCls,"setI","(I)V");
    env->CallVoidMethod(bean,setI,200);

    //static 方法
    jmethodID  printInfo = env->GetStaticMethodID(beanCls,"printInfo","(Ljava/lang/String;)V");
    //创建java字符串
    jstring  str2 = env->NewStringUTF("我是Bean类的静态方法，被C++调用");
    env->CallStaticVoidMethod(beanCls,printInfo, str2);
    //释放局部引用
    env->DeleteLocalRef(str2);


    jmethodID  printInfo2 = env->GetStaticMethodID(beanCls,"printInfo","(Lcom/dongnao/jnitest/Bean2;)V");
    // bean对象
    //在Jni创建java对象：
    jclass bean2Cls = env->FindClass("com/dongnao/jnitest/Bean2");
    //反射创建对象
    //1、获得类的构造方法
    jmethodID constuct = env->GetMethodID(bean2Cls,"<init>","(I)V");
    //2、调用构造方法 创建对象
    jobject  bean2 = env->NewObject(bean2Cls,constuct,88);

    env->CallStaticVoidMethod(beanCls,printInfo2, bean2);
    //后面不再使用bean2了 ，我希望它引用对象占用的内存可以被马上回收
    env->DeleteLocalRef(bean2);


    //
    //修改属性值
    jfieldID  fileId = env->GetFieldID(beanCls,"i","I");
    env->SetIntField(bean,fileId,300);




    env->ReleaseStringUTFChars(str_, str);
    env->DeleteLocalRef(bean2Cls);
}

jclass bean2Cls;
//  extern： 在其他地方声明的
extern void test();


jobject  bean2;
extern "C"
JNIEXPORT void JNICALL
Java_com_dongnao_jnitest_MainActivity_invokeBean2Method(JNIEnv *env, jobject instance) {
    test();
    //局部引用
    //有问题 内部引用被释放
    //指针 指针有值，但是指向的地址数据被释放了  悬空指针
    if (bean2Cls == NULL){
        bean2Cls = env->FindClass("com/dongnao/jnitest/Bean2");
    }
    jmethodID constuct = env->GetMethodID(bean2Cls,"<init>","(I)V");


    jobject  bean = env->NewObject(bean2Cls,constuct,88);
    //  ::bean2 会用外面的bean2
    bean2 = env->NewWeakGlobalRef(bean);
    // 释放弱全局引用
    //env->DeleteWeakGlobalRef(bean2);

    //弱引用 ：不会阻止回收
    //问题： 当我们使用弱引用的时候  弱引用 引用的对象可能被回收了
    //使用的时候判断是否被回收了

    //对一个弱引用 与NULL相比较
    // true： 释放了
    // false: 还可以使用这个弱引用
    jboolean isEqual = env->IsSameObject(bean2, NULL);
}



extern "C"
JNIEXPORT void JNICALL
Java_com_dongnao_jnitest_MainActivity_invokeBean2Method2(JNIEnv *env, jobject instance) {

    if (bean2Cls == NULL){
        jclass cls = env->FindClass("com/dongnao/jnitest/Bean2");
        //把它变成全局引用
        bean2Cls = static_cast<jclass>(env->NewGlobalRef(cls));
        env->DeleteLocalRef(cls);

        //释放全局引用
        //env->DeleteGlobalRef(bean2Cls);
    }
    jmethodID constuct = env->GetMethodID(bean2Cls,"<init>","(I)V");


}



int* a(){
    return 0;
}


int java_cxxs_cxxx_test(){
    int i = *a();
    return i;
}