# CodeAssist Build Engine — bundled toolchain

هذه النسخة هي Android Library باسم `:build_engine` وتضم داخل الـ AAR أدوات البناء نفسها في `assets/toolchain`: aapt2، D8، R8، ECJ، Kotlin compiler، bundletool، apksigner، و`android.jar`. حجم الـ AAR النهائي كبير عمدًا لأن الأدوات مضمّنة داخله.

## الدمج

```groovy
// settings.gradle
include ':build_engine'
project(':build_engine').projectDir = file('../build_engine/build_engine')
```

```groovy
// app/build.gradle
dependencies { implementation project(':build_engine') }
```

## الاستخدام الأسهل

عند استخدام `BuildManager(Context)` تستخرج المكتبة الأدوات تلقائيًا إلى مساحة التطبيق الخاصة، ولا تحتاج إلى نسخ aapt2 أو D8 أو android.jar يدويًا:

```java
BuildRequest request = BuildRequest.builder()
    .projectPath(sourceProjectPath)
    .buildType(BuildType.DEBUG_APK)
    .outputPath(outputPath)
    .build();

new BuildManager(this).buildAsync(
    request,
    (status, percentage) -> Log.d("BuildEngine", percentage + "% " + status),
    result -> {
        if (result.success) Log.d("BuildEngine", result.output.toString());
        else Log.e("BuildEngine", result.log);
    }
);
```

لنسخة Release أو AAB:

```java
BuildRequest request = BuildRequest.builder()
    .projectPath(sourceProjectPath)
    .buildType(BuildType.RELEASE_APK) // أو AAB
    .signingConfig(new SigningConfig(keystore, "release", storePassword, keyPassword))
    .build();
```

## بنية المشروع المصدر

```text
source-project/
└── src/main/
    ├── AndroidManifest.xml
    ├── java/
    ├── kotlin/
    └── res/
```

## الاختبارات المنفذة

تم اختبار المشروع على مشروع Android مصغر حقيقي باستخدام الأدوات المضمنة داخل `assets`:

| الناتج | النتيجة |
|---|---|
| Debug APK | نجح |
| Release APK | نجح |
| R8 shrinking/obfuscation | نجح |
| ProGuard mapping | نجح |
| APK signing وV4 idsig | نجح |
| AAB عبر bundletool | نجح |

## قيد مهم متعلق بالمنصة

الأدوات المضمّنة التي يوفرها Android SDK مثل `aapt2` هي ملفات native مبنية لنظام Linux host. لذلك نجح اختبارها في بيئة Linux الحالية. أما تشغيل aapt2 native من داخل هاتف Android فيحتاج نسخة aapt2 مبنية لمعمارية Android نفسها (arm64-v8a/armeabi-v7a)، وليس نسخة Linux. الكود يستخرج الأدوات تلقائيًا، لكن لا يدّعي أن binary Linux سيعمل على كل هاتف Android. أما أدوات Java فيمكن تشغيلها فقط إذا كانت متوافقة مع Android Runtime وقيود مكتباته.

لذلك هذه النسخة تحتوي الأدوات فعليًا وتنجح في خط البناء الكامل على host، بينما تشغيل الخط كاملًا على الجهاز نفسه يحتاج native Android builds للأدوات أو طبقة تشغيل host مناسبة.
