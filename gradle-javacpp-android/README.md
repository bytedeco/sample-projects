This sample project demonstrates how to integrate [Gradle JavaCPP](https://github.com/bytedeco/gradle-javacpp) with Android Studio.

It was created by following the instructions available at:
  * https://github.com/bytedeco/gradle-javacpp#integration-with-android-studio

Along with the `NativeLibrary.h` example file from:
  * https://github.com/bytedeco/javacpp#accessing-native-apis

## Instructions
To generate jni stuffs, run:
```shell
./gradlew :app:javacppCompileJavaDebug  && ./gradlew :app:javacppBuildParserDebug && ./gradlew :app:javacppBuildCompilerDebug
```

Then simply press run button in Android Studio.

----
Author: [Samuel Audet](https://github.com/saudet/)  
Sponsor: [Iuliu Horga](https://github.com/iuliuh) via [xs:code](https://xscode.com/)
