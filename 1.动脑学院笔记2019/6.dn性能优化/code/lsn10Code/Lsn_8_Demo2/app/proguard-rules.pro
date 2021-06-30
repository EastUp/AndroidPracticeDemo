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

#如果类中有native方法，就不参加混淆
#-keepclasseswithmembers class  com.example.administrator.lsn_8_demo.User{
#		native <methods>;
#	}

#只让一个类中的某个成员或某个方法不加参
#-keep class com.example.administrator.lsn_8_demo.User{
#   		public static void *();
#   		java.lang.String *;
#	}

-keepclassmembers class com.example.administrator.lsn_8_demo.User{
                     		public static void *();
                     		java.lang.String *;
                  	}

-keepattributes SourceFile,LineNumberTable