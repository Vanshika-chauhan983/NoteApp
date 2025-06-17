# Note App

This is a simple yet powerful Note-Taking Android application built using **Jetpack Compose**, **Room Database**, and **Clean Architecture**. 
The app follows modern Android development practices and is structured for scalability and maintainability.

## Features

- Create, edit and delete notes
- Undo delete action using Snackbar
- Sort notes by date, title or color
- Persistent local storage using Room
- Built entirely with Jetpack Compose
- Clean Architecture for separation of concerns

## Architecture Overview

The application is based on Clean Architecture principles with a clear separation between the following layers:

- **Presentation Layer**: Built with Jetpack Compose and ViewModels, responsible for UI and user interaction
- **Domain Layer**: Contains business logic, use-cases, and domain models
- **Data Layer**: Manages data access through Room and repository implementations

## Tech Stack

- Kotlin
- Jetpack Compose
- Room Database
- Hilt for Dependency Injection
- Kotlin Coroutines and Flow for asynchronous and reactive programming
