package dependencies

object Dep {

    const val gson = "com.google.code.gson:gson:2.8.6"

    const val lottie = "com.airbnb.android:lottie:3.3.0"

    const val animator = "jp.wasabeef:recyclerview-animators:3.0.0"

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:4.0.0"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    }

    object Facebook {
        const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"
    }

    object Test {
        const val mockito = "org.mockito:mockito-core:3.4.4"
        const val mockitok = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
        const val junit = "junit:junit:4.12"
        const val testRunner = "androidx.test:runner:1.1.0"
        const val androidJunit4 = "androidx.test.ext.junit:1.1.0"
        const val archCore = "androidx.arch.core:core-testing:2.0.0"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        const val kotlinTestAssertions = "io.kotlintest:kotlintest-assertions:3.1.10"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.3.0-alpha01"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.0-alpha03"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta6"
        const val design = "com.google.android.material:material:1.2.0-alpha06"
        const val coreKtx = "androidx.core:core-ktx:1.5.0-alpha01"
        const val fragment = "androidx.fragment:fragment:1.2.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.4"
        const val activity = "androidx.activity:activity-ktx:1.1.0"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
        const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata:2.2.0"
        const val sharedPref = "androidx.preference:preference-ktx:1.1.0"
    }

    object Kotlin {
        const val version = "1.3.72"
        const val stdlibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:$version"
        const val stdlibJvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"

        const val coroutinesVersion = "1.3.5"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val androidCoroutinesDispatcher =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
        const val coroutinesReactive =
                "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$coroutinesVersion"
        const val coroutinesPlayServices =
                "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutinesVersion"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
        const val coroutinesDebug = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutinesVersion"

    }

    object Dagger {
        const val version = "2.28"
        const val core = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val androidSupport = "com.google.dagger:dagger-android-support:$version"
        const val android = "com.google.dagger:dagger-android:$version"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }


    object Okhttp {
        const val version = "4.2.2"
        const val client = "com.squareup.okhttp3:okhttp:$version"
        const val log = "com.squareup.okhttp3:logging-interceptor:$version"
        const val ktor = "io.ktor:ktor-client-okhttp:1.3.1"
    }

    object Retrofit {
        const val version = "2.9.0"
        const val core = "com.squareup.retrofit2:retrofit:$version"
        const val gson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Timber {
        const val android = "com.jakewharton.timber:timber:4.7.1"
    }

}
