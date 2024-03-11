import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

fun getStringApiKey(propertyKey: String): String {
    return "\"${gradleLocalProperties(rootDir).getProperty(propertyKey)}\""
}

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
        manifestPlaceholders["NAVER_CLIENT_ID"] = getApiKey("naver_client_id")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "NAVER_CLIENT_KEY", getStringApiKey("naver_client_id"))
        buildConfigField("String", "NAVER_SECRET_KEY", getStringApiKey("naver_secret_key"))
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${rootProject.extra["core-ktxVersion"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
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
//    implementation("com.tbuonomo:dotsindicator:5.0")
//    implementation("androidx.compose.foundation:foundation:1.4.0")
    implementation ("com.google.accompanist:accompanist-pager:${rootProject.extra["accompanist_version"]}") // Pager
    implementation ("com.google.accompanist:accompanist-pager-indicators:${rootProject.extra["accompanist_version"]}") // Pager Indicators

    // okHttp, requestBodu 사용하기 위함
    implementation("com.squareup.okhttp3:okhttp:4.9.1")

    // retrofit
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.0")

    // retrofit xml parsing
//    implementation ("com.tickaroo.tikxml:annotation:0.8.13")
//    implementation ("com.tickaroo.tikxml:core:0.8.13")
//    implementation ("com.tickaroo.tikxml:retrofit-converter:0.8.13")
//    kapt ("com.tickaroo.tikxml:processor:0.8.13'")
//    kapt("groupId:artifactId:1.9.22")

    // okhttp
    implementation("com.squareup.okhttp3:okhttp:4.8.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.8.0")

    // Naver Map SDK
    implementation("com.naver.maps:map-sdk:3.17.0")
    implementation("io.github.fornewid:naver-map-compose:1.6.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("io.github.fornewid:naver-map-location:21.0.1")

//    implementation("com.google.android.material:material:1.11.0")

    // Naver Map clustering
    implementation("io.github.ParkSangGwon:tedclustering-naver:1.0.2")

    // compose 에서 권한 요청
    implementation ("com.google.accompanist:accompanist-permissions:0.34.0")

    // coil
    implementation("io.coil-kt:coil-compose:2.6.0")
}