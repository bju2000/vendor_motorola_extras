#include "jni.h"
#include <hardware/hardware.h>
#include <hidl/HidlSupport.h>
#include <hidl/LegacySupport.h>
#include <hidl/Status.h>
#include <vendor/fpsensor/hardware/parts/1.0/ISwapOnData.h>

using android::sp;
using vendor::fpsensor::hardware::parts::V1_0::ISwapOnData;

static sp<ISwapOnData> service = ISwapOnData::getService();

extern "C" JNIEXPORT void JNICALL
Java_com_fpsensor_motoextras_interfaces_Swap_setSize(JNIEnv /*env*/,
                                                          jclass /*clazz*/,
                                                          jint size) {
  service->setSwapSize(size);
}

extern "C" JNIEXPORT void JNICALL
Java_com_fpsensor_motoextras_interfaces_Swap_setSwapOn(JNIEnv /*env*/,
                                                            jclass /*clazz*/,
                                                            jboolean enable) {
  if (enable) {
    service->setSwapOn();
  } else {
    service->setSwapOff();
  }
}
