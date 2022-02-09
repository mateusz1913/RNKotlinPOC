// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra.apply {
        set("buildToolsVersion", "30.0.2")
        set("minSdkVersion", 21)
        set("compileSdkVersion", 30)
        set("targetSdkVersion", 30)
        set("ndkVersion", "21.4.7075529")
        set("kotlinVersion", "1.6.10")
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val kotlinVersion: String by rootProject.extra
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        maven {
            // All of React Native (JS, Obj-C sources, Android binaries) is installed from npm
            url = uri("$rootDir/../node_modules/react-native/android")
        }
        maven {
            // Android JSC is installed from npm
            url = uri("$rootDir/../node_modules/jsc-android/dist")
        }
        mavenCentral {
            // We don't want to fetch react-native from Maven Central as there are
            // older versions over there.
            content {
                excludeGroup("com.facebook.react")
            }
        }
        google()
        maven {
            url = uri("https://www.jitpack.io")
        }
    }
}
