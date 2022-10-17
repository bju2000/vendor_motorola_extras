# Motorola specific device features 

This is a vendor addon to add in your android builds to get moto specific things like moto dolby, live walls and more  
**WARNING! This apps exept MotoParts are for 64 Bit devices**

## How to add in your builds

in your device tree add this flags

```
$(call inherit-product, vendor/motorola/extras/Android.mk)
```
Obligatory!

```
MOTOEXTRAS_MOTOCAMERA2 := true
```
By: [Nemesis Developers](https://github.com/NemesisDevelopers)

```
MOTOEXTRAS_DOLBY := true
```
By: [Nemesis Developers](https://github.com/NemesisDevelopers)
this need more additions, check MotoDolby/README.md

```
MOTOEXTRAS_LIVEWALLS := true
```

```
$(call inherit-product, vendor/motorola/extras/MotoParts/Android.mk)
```
This MotoParts by FPSensor but is based in [Eureka Team](https://github.com/eurekadevelopment) SamsungParts

```
MOTOEXTRAS_WIDGET := true
```

And thank you very much [Royna](https://github.com/roynatech2544) for all of your help wen making this.
