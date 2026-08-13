import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.app.shared)

            implementation(libs.compose.ui)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.compose)

            implementation(libs.ktor.client.js.wasm.js)
        }
        wasmJsMain.dependencies {
            implementation(npm("@js-joda/timezone", libs.versions.js.joda.timezone.get()))
        }
    }
}