/* DO NOT EDIT THIS FILE - it is machine generated */
#include "jni.h"
/* Header for class com_east_jni10_Sample */

#ifndef _Included_com_east_jni10_Sample
#define _Included_com_east_jni10_Sample
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_east_jni10_Sample
 * Method:    sort
 * Signature: ([I)V
 */
JNIEXPORT void JNICALL Java_com_east_jni10_Sample_sort
  (JNIEnv *, jclass, jintArray);

JNIEXPORT void JNICALL Java_com_east_jni10_Sample_localRef
(JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_com_east_jni10_Sample_saveGlobalRef
(JNIEnv *, jclass,jstring);	

JNIEXPORT jstring JNICALL Java_com_east_jni10_Sample_getGlobalRef
(JNIEnv *, jclass);	

JNIEXPORT void JNICALL Java_com_east_jni10_Sample_deleteGlobalRef
(JNIEnv *, jclass);	

JNIEXPORT void JNICALL Java_com_east_jni10_Sample_staticLocalCache
(JNIEnv *, jclass,jstring);

JNIEXPORT void JNICALL Java_com_east_jni10_Sample_initStaticCache
(JNIEnv *, jclass);	

JNIEXPORT void JNICALL Java_com_east_jni10_Sample_exception
(JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
