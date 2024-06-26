import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

val properties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}
//properties.

//fun getApiKey(propertyKey: String): String {
//    return gradleLocalProperties(rootDir).getProperty(propertyKey)
//
//}
//
//fun getStringApiKey(propertyKey: String): String {
//    return "\"${gradleLocalProperties(rootDir).getProperty(propertyKey)}\""
//}

android {
    namespace = "com.example.planet"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        manifestPlaceholders += mapOf()
        applicationId = "com.example.planet"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String
        manifestPlaceholders["NAVER_CLIENT_ID"] = properties.getProperty("naver_client_id")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        buildConfigField("String", "NAVER_CLIENT_KEY", getStringApiKey("naver_client_id"))
//        buildConfigField("String", "NAVER_SECRET_KEY", getStringApiKey("naver_secret_key"))
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            buildFeatures.buildConfig = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
//            isShrinkResources = true
//            isMinifyEnabled = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro",
//            )
            buildConfigField("String", "NAVER_CLIENT_KEY", "\"${properties.getProperty("naver_client_id")}\"")
            buildConfigField("String", "NAVER_SECRET_KEY", "\"${properties.getProperty("naver_secret_key")}\"")
        }
        debug {
            buildConfigField("String", "NAVER_CLIENT_KEY", "\"${properties.getProperty(" naver_client_id ")}\"")
            buildConfigField("String", "NAVER_SECRET_KEY", "\"${properties.getProperty("naver_secret_key")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
//        freeCompilerArgs += listOf(
//            "-P",
//            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${rootProject.file(".").absolutePath}/compose_compile"
//        )

    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src/main/assets")
            }
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${rootProject.extra["core-ktxVersion"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycle-runtime-ktx"]}")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui:1.6.2")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.wear.compose:compose-material:1.3.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
//    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
//    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
//    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
//    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
//    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // hilt
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-compiler:2.50")   // Hilt compiler
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0") // compose에서 hilt

    // material icon,  https://fonts.google.com/icons
    implementation("androidx.compose.material:material-icons-extended:${rootProject.extra["compose_ui_version"]}")

    // vico
    implementation("com.patrykandpatrick.vico:compose:${rootProject.extra["vico-version"]}") // For Jetpack Compose.
    implementation("com.patrykandpatrick.vico:compose-m3:${rootProject.extra["vico-version"]}") // For `compose`. Creates a `ChartStyle` based on an M3 Material Theme.

    // viewPager2
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation("androidx.compose.foundation:foundation:1.6.6")
    // accompanist-pager는 지원 중단
//    implementation ("com.google.accompanist:accompanist-pager:${rootProject.extra["accompanist_version"]}") // Pager
//    implementation ("com.google.accompanist:accompanist-pager-indicators:${rootProject.extra["accompanist_version"]}") // Pager Indicators

    // okHttp, requestBody 사용하기 위함
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // retrofit
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.0")

    // okhttp
    implementation("com.squareup.okhttp3:okhttp:4.8.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.8.0")

    // Naver Map SDK
    implementation("com.naver.maps:map-sdk:3.11.0")
    implementation("io.github.fornewid:naver-map-compose:1.6.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("io.github.fornewid:naver-map-location:21.0.1")

    // Naver Map clustering
    implementation("io.github.ParkSangGwon:tedclustering-naver:1.0.2")

    // compose 에서 권한 요청
    implementation ("com.google.accompanist:accompanist-permissions:0.34.0")

    // coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    //paging 3
    implementation ( "androidx.paging:paging-runtime-ktx:3.2.1")
    implementation ("androidx.paging:paging-compose:3.3.0-alpha02")

    // collectAsStateWithLifecycle 사용
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // immutable 객체
    implementation ("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
}