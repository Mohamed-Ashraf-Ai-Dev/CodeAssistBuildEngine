# GitHub and JitPack

Repository: `CodeAssistBuildEngine`

After tagging `v1.0.0`, the JitPack dependency is:

```groovy
repositories { maven { url 'https://jitpack.io' } }
dependencies { implementation 'com.github.Mohamed-Ashraf-Ai-Dev:codeassistbuildengine:v1.0.1' }
```

For Kotlin DSL:

```kotlin
repositories { maven { url = uri("https://jitpack.io") } }
dependencies { implementation("com.github.Mohamed-Ashraf-Ai-Dev:codeassistbuildengine:v1.0.1") }
```

The complete bundled AAR is attached to the GitHub Release as `build_engine-release.aar`.
