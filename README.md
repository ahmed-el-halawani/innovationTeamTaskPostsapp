<img width="315" height="647" alt="image" src="https://github.com/user-attachments/assets/2a0a7ed2-f724-4054-b9b7-1dd17f7644f9" /># Posts App 📱

A modern Android application demonstrating **Clean Architecture**, **MVVM**, and **Paging 3** with offline capabilities. This project serves as a showcase of best practices in Android development, including Dependency Injection (Hilt), Reactive Programming (Coroutines/Flow), and robust error handling.

<a href="https://appetize.io/embed/b_kzbtomvexrmro4wf4cdnrk5lgu?autoplay=true">
  <img src="docs/preview.png" width="250" alt="Click to run app" title="Click to run app directly in browser">
</a>

## 🚀 Features

- **Clean Architecture**: Strictly separated into **Presentation**, **Domain**, and **Data** layers.
- **MVVM Pattern**: ViewModels manage UI state using `StateFlow` and `UiState` wrappers.
- **Paging 3**: Efficient data loading in chunks of 10 items from the local database.
- **Offline Support**: Single source of truth is the local **Room Database**. Applications works seamlessly offline.
- **Dependency Injection**: Fully modularized using **Hilt**.
- **Network Resilience**: Custom `NetworkMonitor` and safe API call wrappers.
- **Modern UI**: XML layouts with ViewBinding, Navigation Component (Safe Args), and Swipe-to-Refresh.

## 🛠️ Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture**: Clean Architecture + MVVM
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Network**: [Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/) (Logging Interceptor)
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room)
- **Asynchrony**: [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Pagination**: [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3) (RemoteMediator pattern simulated via Repository logic)
- **Image Loading**: [Glide](https://github.com/bumptech/glide)
- **Navigation**: [Navigation Component](https://developer.android.com/guide/navigation)
- **Testing**: JUnit 4, MockK, Coroutines Test

## 🏗️ Architecture Overview

The app follows the "Separation of Concerns" principle:

1.  **Domain Layer**: Contains `Models`, `Repository Interfaces`, and `UseCases`. It has **no dependencies** on Android frameworks.
2.  **Data Layer**: Implements the Domain repositories. Handles data operations (Room, Retrofit) and mapping (`Dto` -> `Entity` -> `Domain`).
3.  **Presentation Layer**: Contains `Fragments`, `ViewModels`, and UI-specific logic. Observes data from `UseCases`.

### Package Structure
```
com.halawany.innovationteamtaskpostsapp
├── core            # Common utilities (UiState, NetworkMonitor, Wrappers)
├── data            # Repository Impl, API, Database, Mappers
├── di              # Hilt Modules
├── domain          # Models, Repository Interfaces, UseCases
└── presentation    # Fragments, ViewModels, Adapters
    ├── post.news   # News Feed Screen
    └── post.details# Post Details Screen
```

## 🧪 Testing

The project includes unit tests for ViewModels and UseCases using **MockK** and **CoroutinesTest**.

To run tests:
```bash
./gradlew testDebugUnitTest
```
