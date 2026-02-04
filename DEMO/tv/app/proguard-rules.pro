# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Retrofit and OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

# Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
    <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# GSON
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }

# ExoPlayer
-keep class com.google.android.exoplayer2.** { *; }
-dontwarn com.google.android.exoplayer2.**

# Hilt
-keep class * extends dagger.hilt.android.flags.HiltWrapper_HiltWrapperFlag
-keep class * extends dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix.FragmentGetContextFixFlag
-keep class dagger.hilt.internal.GeneratedComponentManagerHolder

# Room
-keep class * extends androidx.room.RoomDatabase {
    *;
}

# Keep model classes
-keep class com.demo.streamflix.data.model.** { *; }

# Keep API responses
-keep class com.demo.streamflix.data.network.ApiService { *; }
-keep class com.demo.streamflix.data.network.ApiResponse { *; }

# Keep view models
-keep class com.demo.streamflix.ui.*.viewmodel.** { *; }

# Keep custom views and adapters
-keep class com.demo.streamflix.ui.*.adapters.** { *; }
-keep class * extends androidx.recyclerview.widget.RecyclerView.Adapter {
    *;
}

# General rules
-keep class androidx.lifecycle.** { *; }
-keep class androidx.navigation.** { *; }

# Kotlin metadata
-keepclassmembers class ** {
    @org.jetbrains.annotations.NotNull *;
    @org.jetbrains.annotations.Nullable *;
}

# General rules for data binding
-keep class androidx.databinding.** { *; }
-keep class * extends androidx.databinding.DataBinderMapper {
    *;
}

# Keep resources if needed
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Remove logs in release
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}