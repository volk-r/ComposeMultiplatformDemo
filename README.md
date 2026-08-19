# Compose Multiplatform Demo Project

This is a comprehensive Kotlin Multiplatform (KMP) project demonstrating shared UI and logic across **Android, iOS, Web (Wasm/JS), Desktop (JVM)**, and a **Ktor Server**.

## 🚀 Project Structure

*   **`:app:shared`**: The heart of the application. Contains shared Compose UI, ViewModels, and business logic.
    *   `commonMain`: Core UI and logic shared by all targets.
    *   `androidMain`, `appleMain`, `jvmMain`, `webMain`: Platform-specific implementations (e.g., `NativeButton`, `DataStore` initialization).
    *   `nonWebMain` / `nonJvmMain`: Shared source sets for specific target groups.
*   **`:app:androidApp`**: Android-specific entry point and configuration.
*   **`:app:iosApp`**: Xcode project and SwiftUI entry point for iOS.
*   **`:app:desktopApp`**: Entry point for the Compose Desktop application.
*   **`:app:webApp`**: Entry point for the Web (Kotlin/Wasm & JS) application.
*   **`:core`**: Shared utilities and domain models used by both the app and the server.
*   **`:server`**: A Ktor-based backend server.

## ✨ Key Features

*   **Shared UI**: Built with **Compose Multiplatform** for consistent design across all platforms.
*   **Data Persistence**: Uses **Jetpack DataStore** (Preferences) with platform-specific implementations for storage paths.
*   **Networking**: **Ktor Client** with platform-specific engines (Darwin for iOS, OkHttp for Android/JVM).
*   **Dependency Injection**: **Koin** for managing dependencies across all modules.
*   **Native Integration**: Demonstrates `expect/actual` patterns for native UI components (e.g., `NativeButton` using `UIKit` on iOS).

## 🛠 Getting Started

### Prerequisites
*   Android Studio (latest stable or Koala+)
*   Xcode (for iOS development)
*   JDK 17 or higher

### Running the Applications

| Target | Command |
| :--- | :--- |
| **Android** | `./gradlew :app:androidApp:assembleDebug` (or run from IDE) |
| **Desktop** | `./gradlew :app:desktopApp:run` |
| **Web (Wasm)** | `./gradlew :app:webApp:wasmJsBrowserDevelopmentRun` |
| **Server** | `./gradlew :server:run` |
| **iOS** | Open `app/iosApp/iosApp.xcodeproj` in Xcode and run. |

### Build & Troubleshooting

If you encounter issues with the Wasm lock file during the build:
```bash
./gradlew kotlinWasmUpgradeYarnLock
```

## 🧪 Testing

*   **Android**: `./gradlew :app:shared:testAndroidHostTest`
*   **Desktop**: `./gradlew :app:shared:jvmTest`
*   **Web**: `./gradlew :app:shared:wasmJsTest`
*   **iOS**: `./gradlew :app:shared:iosSimulatorArm64Test`
*   **Server**: `./gradlew :server:test`

---
*Built with ❤️ using Kotlin Multiplatform.*
