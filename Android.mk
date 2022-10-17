#
# Copyright (C) 2022 FPSensor
#
# SPDX-License-Identifier: Apache-2.0
#

#PRODUCT_SOONG_NAMESPACES += \
#    vendor/motorola/extras

# Motorola Additions for a better UI experience in Custom ROMs
# Tested in Moto E5+

ifeq ($(MOTOEXTRAS__MOTOCAMERA2), true)
PRODUCT_PACKAGES += \
    MotCamera2 \
    MotCamera2Tunner
endif

ifeq ($(MOTOEXTRAS_DOLBY), true)
PRODUCT_PACKAGES += \
    MotoDolbyV3

$(call inherit-product, vendor/motorola/extras/MotoDolby/config.mk)
endif

ifeq ($(MOTOEXTRAS_LIVEWALLS), true)
PRODUCT_PACKAGES += \
    MotoLiveWallpaper2 \
    MotoLiveWallpaper3 
endif

ifeq ($(MOTOEXTRAS_WIDGET), true)
PRODUCT_PACKAGES += \
    MotoClockWidget
endif

# Thank you very much Nemesis Developers for MotoCamera2
