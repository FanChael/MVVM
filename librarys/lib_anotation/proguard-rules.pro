# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 自定义类的混淆
# keep annotated by NotProguard  ---- like this: -keep class com.example.test.xxxBean {*;}
#                                ---- like this: -keep class com.example.test.** {*;}
# 下面这块主要针对实体类处理，后面如果有地方需要添加一些不被混淆的变量，函数都可以通过添加@NotProguard来注解
-keep @com.hl.anotation.NotProguard class * {*;}
-keep class * {
@com.hl.anotation.NotProguard <fields>;
}
-keepclassmembers class * {
@com.hl.anotation.NotProguard <methods>;
}