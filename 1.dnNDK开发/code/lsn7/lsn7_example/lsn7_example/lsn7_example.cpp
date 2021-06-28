
// lsn7_example.cpp: 定义应用程序的入口点。
//

#include "jni.h"
#include "ExampleUnitTest.h"

JNIEXPORT jstring JNICALL Java_com_example_jnitest_ExampleUnitTest_test(JNIEnv *env, jobject, jint i, jstring j, jfloat k) {
	// 获得c字符串
	// 开辟内存x，拷贝java字符串到x中  返回指向x的指针
	// 参数2 isCopy
	// 提供一个 boolean (int) 指针，用于接收jvm传给我们的字符串是否是拷贝的
	// 通常，我们不关心这个，一般传个NULL就可以了
	const char* str = env->GetStringUTFChars(j, JNI_FALSE);
	char returnStr[100];
	// 格式化字符串
	sprintf(returnStr, "C++ string:%d,%s,%f", i, str, k);
	//释放掉内存 x
	env->ReleaseStringUTFChars(j, str);
	// 返回Java字符串
	return env->NewStringUTF(returnStr);
}