#include "com_east_jni10_Sample.h"
#include <stdlib.h>

// 4. 定义一个 compare 的方法
int compare(const int* number1, const int* number2){
	return *number1 - *number2;
}

// 数组排序
JNIEXPORT void JNICALL Java_com_east_jni10_Sample_sort
(JNIEnv* env, jclass jclz, jintArray jarray){
	
	// 2. 获取 jintArray 的首地址
	jint* intArray = (*env)->GetIntArrayElements(env,jarray,NULL);

	// 3. 获取数组的长度
	int length = (*env)->GetArrayLength(env,jarray);

	// 1. 对 jarray 进行排序
	// void qsort(void * _Base, size_t _NumOfElements, size_t _SizeOfElements, int(*_PtFuncCompare)(const void *, const void *))
	// 第一个参数：void* 数组的首地址
	// 第二个参数：数组的长度
	// 第三个参数：数组元素数据类型的大小
	// 第四个参数：数组的一个比较方法指针（Comparable）
	qsort(intArray,length,sizeof(int),compare);

	// 同步数组的数据给 java 数组 intArray 并不是 jarray， 可以简单理解为 copy
	// JNI_OK == 0:：既要同步数据给 jarray， 又要释放 intArray
	// JNI_COMMIT == 1：会同步数据给 jarray, 但是不会释放 intArray
	// JNI_ABORT == 2：不会同步数据给 jarray, 但是会释放 intArray

	(*env)->ReleaseIntArrayElements(env, jarray, intArray, JNI_OK);

}

// 局部引用
JNIEXPORT void JNICALL Java_com_east_jni10_Sample_localRef
(JNIEnv * env, jclass jclz){
	// 在 native 层构建的 Java 对象，不用了应该怎么管理（应该进行回收删除）
	// native 层开辟的内存由谁管理，你能开辟多大

	// 字符串截取，String 对象
	jclass str_clz = (*env)->FindClass(env,"java/lang/String");
	jmethodID jmid = (*env)->GetMethodID(env, str_clz, "<init>", "()V");
	jobject j_str = (*env)->NewObject(env,str_clz,jmid);

	// 还有 100 行代码

	// jobject 不要再使用了，要回收 javaGC 的源码
	(*env)->DeleteLocalRef(env,j_str);

	// 删除了就不能再使用了，C 和 C++ 都需要自己释放内存（静态开辟的不需要，动态开辟的需要）
}

// 全局引用

jstring globalStr;

JNIEXPORT void JNICALL Java_com_east_jni10_Sample_saveGlobalRef
(JNIEnv * env, jclass j_clz, jstring value){
	// 保存全局引用，其它方法需要用到
	globalStr = (*env)->NewGlobalRef(env,value);

	// NewWeakGlobalRef （java 中的软引用很像）无法保证对象不为空
}

JNIEXPORT jstring JNICALL Java_com_east_jni10_Sample_getGlobalRef
(JNIEnv * env, jclass j_clz){
	return globalStr;
}


JNIEXPORT void JNICALL Java_com_east_jni10_Sample_deleteGlobalRef
(JNIEnv * env, jclass j_clz){
	(*env)->DeleteGlobalRef(env,globalStr);
}

// 局部静态缓存
JNIEXPORT void JNICALL Java_com_east_jni10_Sample_staticLocalCache
(JNIEnv * env, jclass j_clz, jstring value){
	// name 属性，赋值操作
	static jfieldID jfid = NULL; // 局部静态缓存，不会反复的去获取 jfieldID
	
	if (jfid){
		printf("fieldID is not null\n");
	}
	else{
		jfid = (*env)->GetStaticFieldID(env, j_clz, "name", "Ljava/lang/String;");// 如果没有静态缓存 这个方法会被多次调用，会去反复的获取 jfieldID
	}

	(*env)->SetStaticObjectField(env,j_clz,jfid,value);
}


// 全局静态缓存
static jfieldID f_name_id = NULL;
static jfieldID f_name1_id = NULL;
static jfieldID f_name2_id = NULL;


//JNIEXPORT void JNICALL Java_com_east_jni10_Sample_staticLocalCache
//(JNIEnv * env, jclass j_clz, jstring value){
//	// 因为是静态缓存，所以这个方法被反复调用，也不会反复的去获取 jfieldID
//	(*env)->SetStaticObjectField(env,j_clz,f_name_id,value);
//}


JNIEXPORT void JNICALL Java_com_east_jni10_Sample_initStaticCache
(JNIEnv *env, jclass j_clz){
	// 初始化全局静态缓存
	f_name_id = (*env)->GetStaticFieldID(env, j_clz, "name", "Ljava/lang/String;");
	f_name1_id = (*env)->GetStaticFieldID(env, j_clz, "name1", "Ljava/lang/String;");
	f_name2_id = (*env)->GetStaticFieldID(env, j_clz, "name2", "Ljava/lang/String;");
}

// 异常处理
JNIEXPORT void JNICALL Java_com_east_jni10_Sample_exception
(JNIEnv *env, jclass j_clz){

	// 假设现在想给 ，name3 赋值
	jfieldID jfid = (*env)->GetStaticFieldID(env, j_clz, "name3", "Ljava/lang/String;");

	// 两种方式解决
	// 1. 补救措施， 不拿 name3 拿 name
	// 1.1 有没有异常
	jthrowable throwable = (*env)->ExceptionOccurred(env);
	/*if (throwable){
		// 补救措施，先把异常清除
		printf("native have a exception");
		// 清除异常
		(*env)->ExceptionClear(env);
		// 重新获取 name 属性
		jfid = (*env)->GetStaticFieldID(env, j_clz, "name", "Ljava/lang/String;");
	}*/

	// 2. 想给 java 层抛一个异常
	if (throwable){
		// 清除异常
		(*env)->ExceptionClear(env);
		// 给 java 层抛 一个 Throwable 异常

		// 第一种方式，直接把异常抛给 java 层
		(*env)->Throw(env, throwable);

		// 第二种方式抛异常
		// (*env)->ThrowNew(env, no_such_clz, "NoSuchFieldException name3");

		return; //  必须 return 如果不的话，程序会接着往下运行，肯定还会crash
	}

	jstring name = (*env)->NewStringUTF(env, "Eastrise");
	(*env)->SetStaticObjectField(env, j_clz, jfid, name);

}