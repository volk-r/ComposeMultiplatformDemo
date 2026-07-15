import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.koin)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-Xoverride-konan-properties=llvmHome.macos_arm64=/usr")
        }
    }
    
    jvm()
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    android {
       namespace = "com.example.composempdemo.app.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
       withDeviceTest {}
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.datastore)
            implementation(libs.datastore.preferences)
        }
        val androidHostTest by getting {
            dependencies {
                implementation(libs.compose.ui.test.junit4.android)
                implementation(libs.compose.ui.test.manifest)
                implementation(libs.robolectric)
            }
        }
        commonMain.dependencies {
            api(projects.core)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.navigation.compose)

            api(libs.datastore.preferences.core)

            implementation(libs.bundles.ktor)
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        appleMain.dependencies {
            implementation(libs.datastore)
            implementation(libs.datastore.preferences)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)

            implementation(libs.oshi.core)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.datastore)
            implementation(libs.datastore.preferences)
        }
        jvmTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.assertk)
        }
        webMain.dependencies {
            implementation(libs.datastore.core.okio)
            implementation(libs.datastore.preferences.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(kotlin("test-annotations-common"))
            implementation(libs.assertk)

            implementation(libs.compose.ui.test)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.test)
        }
    }

    listOf("androidMain", "appleMain", "jvmMain").forEach { sourceSetName ->
        sourceSets.getByName(sourceSetName).kotlin.srcDir("src/nonWebMain/kotlin")
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}