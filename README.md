# Boring App

A modern Android application for boring purposes...

## Project Overview

Boring App is a sample application that demonstrates how to build a modern Android app following 
recommended architecture patterns and using the latest libraries and tools. The app fetches user data from a 
remote API (DummyJSON) and displays it in a paginated list, with support for offline caching and detailed user views.

## Project Structure

The project follows a feature-based package structure with clean architecture principles:

```
app/src/main/java/dev/hirogakatageri/boring/
├── core/                  # Core modules for dependency injection
│   ├── DatabaseModule.kt  # Room database dependencies
│   ├── NetworkModule.kt   # Retrofit and API service dependencies
│   ├── RepositoryModule.kt # Repository dependencies
│   └── ViewModelModule.kt # ViewModel dependencies
├── data/                  # Data layer components
│   └── AppDatabase.kt     # Room database configuration
├── user/                  # User feature
│   ├── activity/          # Activities for user feature
│   ├── local/             # Local data source (Room DAOs)
│   ├── model/             # Data models
│   ├── remote/            # Remote data source (API services)
│   ├── repository/        # Repositories
│   ├── view/              # Compose UI components
│   └── viewmodel/         # ViewModels
├── util/                  # Utility classes
├── BoringApplication.kt   # Application class
└── HomeActivity.kt        # Main activity
```

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern with clean architecture principles:

- **Model**: Represents the data layer, including repositories, data sources (local and remote), and data models.
- **View**: Implemented using Jetpack Compose, responsible for rendering the UI and forwarding user actions to the ViewModel.
- **ViewModel**: Acts as a bridge between the View and the Model, handling UI logic and exposing state to the View.

### Key Components:

1. **Repository Pattern**: Provides a clean API to the rest of the app for data operations, abstracting the data sources.
2. **Dependency Injection**: Uses Koin for dependency injection, making the code more modular and testable.
3. **Offline-First Approach**: Implements caching with Room database for offline support.
4. **Reactive Programming**: Uses Kotlin Flow for asynchronous data streaming.

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio (Arctic Fox or newer)
3. Sync Gradle files
4. Run the app on an emulator or physical device

## Dependencies

The app uses the following major dependencies:

- **Jetpack Compose**: For building the UI
- **Koin**: For dependency injection
- **Retrofit**: For network requests
- **Moshi**: For JSON parsing
- **Room**: For local database storage
- **Coil**: For image loading
- **Coroutines & Flow**: For asynchronous operations
- **Lifecycle Components**: For lifecycle-aware components

## Guidelines for Contribution

### Code Style

- Follow Kotlin coding conventions
- Use meaningful names for variables, functions, and classes
- Write documentation for public APIs
- Keep functions small and focused on a single responsibility

### Architecture Guidelines

- Maintain separation of concerns between layers
- Avoid direct dependencies between components; use dependency injection
- Handle errors appropriately at each layer
- Write unit tests for repositories and ViewModels

### Adding New Features

1. Create appropriate packages under the feature name
2. Implement the data layer (models, API services, DAOs)
3. Implement the repository layer
4. Implement the ViewModel
5. Implement the UI using Compose
6. Update the dependency injection modules as needed

## License

This project is licensed under the MIT License - see the LICENSE file for details.