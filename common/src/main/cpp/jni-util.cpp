#include <jni.h>
#include <string>

char *sessionKey = "F32DA18EF36EA7D908D59918B3423FA6";

extern "C" JNIEXPORT jstring JNICALL
Java_com_some_common_JniUtil_getFlSign(
        JNIEnv *env,
        jobject /* this */) {
    return env->NewStringUTF("_flsign");
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_some_common_JniUtil_getFlSignId(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("_flsignid");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_some_common_JniUtil_getTimeStamp(JNIEnv *env, jobject thiz) {

    return env->NewStringUTF("_timestamp");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_some_common_JniUtil_getKey1(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("1001");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_some_common_JniUtil_getVaule1(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("724E18F365F1123078413B515BE9D348");
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_some_common_JniUtil_sumArray(JNIEnv *env, jobject thiz, jintArray array) {
    jint buf[10];
    jint i, sum = 0;
    env->GetIntArrayRegion(array, 0, 10, buf);

    for (i = 0; i < 10; i++) {
        sum += buf[i];
    }
    return sum;
}