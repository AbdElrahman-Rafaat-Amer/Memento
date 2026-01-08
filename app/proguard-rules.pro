# Keep crash information readable
-keepattributes SourceFile,LineNumberTable

# Kotlin
-keep class kotlin.Metadata { *; }
-keepclassmembers class **$WhenMappings {
    <fields>;
}

# Hilt - Prevent injection failures
-keep class **_HiltModules { *; }
-keep class **_HiltModules$* { *; }
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }

# Room - Prevent database crashes
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *

# App models - Keep data classes
-keep class com.abdelrahman.raafat.memento.**.model.** { *; }

# Remove debug logs in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}