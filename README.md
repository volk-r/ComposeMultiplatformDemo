# Compose Multiplatform Demo Project

This is a comprehensive Kotlin Multiplatform (KMP) project demonstrating shared UI and logic across **Android, iOS, Web (Wasm/JS), Desktop (JVM)**, and a **Ktor Server**.

## 🚀 Project Structure

*   **`:app:shared`**: The heart of the application. Contains shared Compose UI, ViewModels, and business logic.
    *   `commonMain`: Core UI and logic shared by all targets.
    *   `androidMain`, `appleMain`, `jvmMain`, `webMain`: Platform-specific implementations (e.g., `NativeButton`, `DataStore` initialization).
    *   `nonWebMain` / `nonJvmMain`: Shared source sets for specific target groups (e.g., DataStore path logic).
*   **`:app:androidApp`**: Android-specific entry point.
*   **`:app:iosApp`**: Xcode project and SwiftUI entry point for iOS.
*   **`:app:desktopApp`**: Entry point for the Compose Desktop application.
*   **`:app:webApp`**: Entry point for the Web (Kotlin/Wasm & JS) application.
*   **`:core`**: Shared utilities and domain models used by both the app and the server.
*   **`:server`**: A Ktor-based backend server.

## 🛠 Technology Stack

### Core & Multiplatform
*   **Kotlin Multiplatform (KMP)**: Shared business logic across all targets.
*   **Kotlinx Serialization**: JSON parsing and data serialization.
*   **Kotlinx DateTime**: Multiplatform date and time handling.
*   **Okio**: File system and path management for shared storage.

### User Interface (UI)
*   **Compose Multiplatform**: Declarative UI for all platforms.
    *   **Material 3**: Modern design system implementation.
    *   **Navigation Compose**: Type-safe navigation in shared code.
*   **UIKit Interop**: Integration of native iOS components (e.g., `UIViewController`).

### Architecture & DI
*   **Koin**: Dependency injection for shared and platform-specific modules.
*   **MVVM**: Architecture using `androidx.lifecycle.ViewModel` in shared code.

### Data & Networking
*   **Ktor Client**: Asynchronous HTTP client (Darwin engine for iOS, OkHttp for Android/JVM).
*   **Jetpack DataStore**: Persistent key-value storage (Preferences).

### Backend
*   **Ktor Server**: High-performance backend framework.
*   **Shared Core**: Common models shared between the app and server.

## 🚀 Getting Started

### Prerequisites
*   Android Studio (latest stable or Koala+)
*   Xcode (for iOS development)
*   JDK 17 or higher

### Running the Applications

| Target | Command |
| :--- | :--- |
| **Android** | `./gradlew :app:androidApp:assembleDebug` |
| **Desktop** | `./gradlew :app:desktopApp:run` |
| **Web (Wasm)** | `./gradlew :app:webApp:wasmJsBrowserDevelopmentRun` |
| **Server** | `./gradlew :server:run` |
| **iOS** | Open `app/iosApp/iosApp.xcodeproj` in Xcode and run. |

### Build & Troubleshooting
If you encounter Wasm lock file issues:
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
