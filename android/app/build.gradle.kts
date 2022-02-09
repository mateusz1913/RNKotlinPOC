import com.android.build.VariantOutput
import com.android.build.gradle.api.ApkVariantOutput

plugins {
    id("com.android.application")
    kotlin("android")
}

/**
 * The react.gradle file registers a task for each build variant (e.g. bundleDebugJsAndAssets
 * and bundleReleaseJsAndAssets).
 * These basically call `react-native bundle` with the correct arguments during the Android build
 * cycle. By default, bundleDebugJsAndAssets is skipped, as in debug/dev mode we prefer to load the
 * bundle directly from the development server. Below you can see all the possible configurations
 * and their defaults. If you decide to add a configuration block, make sure to add it before the
 * `apply(from = "../../node_modules/react-native/react.gradle")` line.
 *
 * val react by extra {
 *   mapOf(
 *     // the name of the generated asset file containing your JS bundle
 *     "bundleAssetName" to "index.android.bundle",
 *
 *     // the entry file for bundle generation. If none specified and
 *     // "index.android.js" exists, it will be used. Otherwise "index.js" is
 *     // default. Can be overridden with ENTRY_FILE environment variable.
 *     "entryFile" to "index.android.js",
 *
 *     // https://reactnative.dev/docs/performance#enable-the-ram-format
 *     "bundleCommand" to "ram-bundle",
 *
 *     // whether to bundle JS and assets in debug mode
 *     "bundleInDebug" to false,
 *
 *     // whether to bundle JS and assets in release mode
 *     "bundleInRelease" to true,
 *
 *     // whether to bundle JS and assets in another build variant (if configured).
 *     // See http://tools.android.com/tech-docs/new-build-system/user-guide#TOC-Build-Variants
 *     // The configuration property can be in the following formats
 *     //         'bundleIn${productFlavor}${buildType}'
 *     //         'bundleIn${buildType}'
 *     // "bundleInFreeDebug" to true,
 *     // "bundleInPaidRelease" to true,
 *     // "bundleInBeta" to true,
 *     // whether to disable dev mode in custom build variants (by default only disabled in release)
 *     // for example: to disable dev mode in the staging build type (if configured)
 *     "devDisabledInStaging" to true,
 *
 *     // The configuration property can be in the following formats
 *     //         'devDisabledIn${productFlavor}${buildType}'
 *     //         'devDisabledIn${buildType}'
 *     // the root of your project, i.e. where "package.json" lives
 *     "root" to "../../",
 *
 *     // where to put the JS bundle asset in debug mode
 *     "jsBundleDirDebug" to "$buildDir/intermediates/assets/debug",
 *
 *     // where to put the JS bundle asset in release mode
 *     "jsBundleDirRelease" to "$buildDir/intermediates/assets/release",
 *
 *     // where to put drawable resources / React Native assets, e.g. the ones you use via
 *     // require('./image.png')), in debug mode
 *     "resourcesDirDebug" to "$buildDir/intermediates/res/merged/debug",
 *
 *     // where to put drawable resources / React Native assets, e.g. the ones you use via
 *     // require('./image.png')), in release mode
 *     "resourcesDirRelease" to "$buildDir/intermediates/res/merged/release",
 *
 *     // by default the gradle tasks are skipped if none of the JS files or assets change; this means
 *     // that we don't look at files in android/ or ios/ to determine whether the tasks are up to
 *     // date; if you have any other folders that you want to ignore for performance reasons (gradle
 *     // indexes the entire tree), add them here. Alternatively, if you have JS files in android/
 *     // for example, you might want to remove it from here.
 *     "inputExcludes" to listOf("android/<androidFiles>", "ios/<iOSFiles>"),
 *
 *     // override which node gets called and with what additional arguments
 *     "nodeExecutableAndArgs" to listOf("node"),
 *
 *     // supply additional arguments to the packager
 *     "extraPackagerArgs" to listOf()
 *
 *     // clean and rebuild if changing
 *     "enableHermes" to false
 *   )
 * }
 */

val react by extra {
    mapOf(
        "enableHermes" to false  // clean and rebuild if changing
    )
}

apply(from = "../../node_modules/react-native/react.gradle")

/**
 * Set this to true to create two separate APKs instead of one:
 *   - An APK that only works on ARM devices
 *   - An APK that only works on x86 devices
 * The advantage is the size of the APK is reduced by about 4MB.
 * Upload all the APKs to the Play Store and people will download
 * the correct one based on the CPU architecture of their device.
 */
val enableSeparateBuildPerCPUArchitecture = false

/**
 * Run Proguard to shrink the Java bytecode in release builds.
 */
val enableProguardInReleaseBuilds = false

/**
 * The preferred build flavor of JavaScriptCore.
 *
 * For example, to use the international variant, you can use:
 * `val jscFlavor = "org.webkit:android-jsc-intl:+"`
 *
 * The international variant includes ICU i18n library and necessary data
 * allowing to use e.g. `Date.toLocaleString` and `String.localeCompare` that
 * give correct results when using with locales other than en-US.  Note that
 * this variant is about 6MiB larger per architecture than default.
 */
val jscFlavor = "org.webkit:android-jsc:+"

/**
 * Whether to enable the Hermes VM.
 *
 * This should be set on `val react by extra` and that value will be read here. If it is not set
 * on `val react by extra`, JavaScript will not be compiled to Hermes Bytecode
 * and the benefits of using Hermes will therefore be sharply reduced.
 */
val enableHermes = react.getOrDefault("enableHermes", false)

/**
 * Architectures to build native code for in debug.
 */
val nativeArchitectures = project.properties["reactNativeDebugArchitectures"] as String?

val ndkVersion: String by rootProject.extra
val compileSdkVersion: Int by rootProject.extra
val minSdkVersion: Int by rootProject.extra
val targetSdkVersion: Int by rootProject.extra

val versions by extra {
    mapOf(
        "compileSdkVersion" to compileSdkVersion,
        "minSdkVersion" to minSdkVersion,
        "ndkVersion" to ndkVersion,
        "targetSdkVersion" to targetSdkVersion
    )
}

val FLIPPER_VERSION: String by project

android {
    ndkVersion = versions["ndkVersion"] as String

    compileSdkVersion(versions["compileSdkVersion"] as Int)

    defaultConfig {
        applicationId = "com.rnkotlinpoc"
        minSdkVersion(versions["minSdkVersion"] as Int)
        targetSdkVersion(versions["targetSdkVersion"] as Int)
        versionCode = 1
        versionName = "1.0"
    }
    splits {
        abi {
            reset()
            isEnable = enableSeparateBuildPerCPUArchitecture
            isUniversalApk = false  // If true, also generate a universal APK
            include("armeabi-v7a", "x86", "arm64-v8a", "x86_64")
        }
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }
    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs["debug"]
            if (nativeArchitectures != null) {
                ndk {
                    abiFilters.addAll(nativeArchitectures.split(","))
                }
            }
        }
        getByName("release") {
            // Caution! In production, you need to generate your own keystore file.
            // see https://reactnative.dev/docs/signed-apk-android.
            signingConfig = signingConfigs["debug"]
            isMinifyEnabled = enableProguardInReleaseBuilds
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    // applicationVariants are e.g. debug, release
    applicationVariants.forEach { variant ->
        variant.outputs.filterIsInstance<ApkVariantOutput>().forEach { output ->
            // For each separate APK per architecture, set a unique version code as described here:
            // https://developer.android.com/studio/build/configure-apk-splits.html
            // Example: versionCode 1 will generate 1001 for armeabi-v7a, 1002 for x86, etc.
            val versionCodes = mapOf(
                "armeabi-v7a" to 1,
                "x86" to 2,
                "arm64-v8a" to 3,
                "x86_64" to 4
            )
            val abi = output.getFilter(VariantOutput.FilterType.ABI)
            if (abi != null) {  // null for the universal-debug, universal-release variants
                output.versionCodeOverride =
                    (defaultConfig.versionCode ?: 1) * 1000 + (versionCodes[abi] ?: 0)
            }
        }
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    //noinspection GradleDynamicVersion
    implementation("com.facebook.react:react-native:+")  // From node_modules

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")

    debugImplementation("com.facebook.flipper:flipper:${FLIPPER_VERSION}") {
        exclude(group = "com.facebook.fbjni")
    }

    debugImplementation("com.facebook.flipper:flipper-network-plugin:${FLIPPER_VERSION}") {
        exclude(group = "com.facebook.flipper")
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
    }

    debugImplementation("com.facebook.flipper:flipper-fresco-plugin:${FLIPPER_VERSION}") {
        exclude(group = "com.facebook.flipper")
    }

    if (enableHermes) {
        val hermesPath = "../../node_modules/hermes-engine/android/";
        debugImplementation(files(hermesPath + "hermes-debug.aar"))
        releaseImplementation(files(hermesPath + "hermes-release.aar"))
    } else {
        implementation(jscFlavor)
    }
}

// Run this once to be able to run the application with BUCK
// puts all compile dependencies into folder libs for BUCK to use
task("copyDownloadableDepsToLibs", Copy::class) {
    from(configurations.implementation)
    into("libs")
}

apply {
    from("../../node_modules/@react-native-community/cli-platform-android/native_modules.gradle")
}

val applyNativeModulesAppBuildGradle: groovy.lang.Closure<Any?> by extra

applyNativeModulesAppBuildGradle(project)
