# Weather Android App
This application is built using the Model-View-ViewModel (MVVM) architecture combined with the Repository pattern, effectively separating the user interface, business logic, and data management.

# How It Works
- User Interface: The app opens to a main weather screen displaying the lastly search city or current location.
- City Search: When a user enters a city name in the search bar, the process begins.
- Data Retrieval: The ViewModel interacts with the Repository, which calls the OpenWeatherMap API to fetch the weather data for the specified city.
- Data Handling: Once the API returns the weather information, the Repository passes this data back to the ViewModel.
- Display Update: The ViewModel updates the View with the new weather data, allowing the user to see the current weather conditions for their selected city.
- Persistent Storage: The app uses DataStore to save the last searched city name, ensuring that users can quickly access their most recent queries.

# Benefits of This Architecture
This architecture promotes a clear separation of concerns, making the codebase more scalable and maintainable. By decoupling the UI from the data and business logic, each component can evolve independently, enhancing flexibility and testability. The integration of DataStore also provides a seamless user experience by retaining essential data across sessions.

## 🛠 Tech Stack
- [Kotlin](https://developer.android.com/kotlin) - Most of the Android community uses Kotlin as their preferred choice of language.
- Jetpack:
    - [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.
    - [Android KTX](https://developer.android.com/kotlin/ktx.html) - Android KTX is a set of Kotlin extensions that are included with Android Jetpack and other Android libraries. KTX extensions provide concise, idiomatic Kotlin to Jetpack, Android platform, and other APIs.
    - [AndroidX](https://developer.android.com/jetpack/androidx) - The androidx namespace comprises the Android Jetpack libraries. It's a major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Lifecycle-aware components perform actions in response to a change in the lifecycle status of another component, such as activities and fragments. These components help you produce better-organized, and often lighter-weight code, that is easier to maintain.
    - [Datastore](https://developer.android.com/jetpack/androidx/releases/datastore)- A library that allows to store key-value pairs or typed objects with protocol buffers.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel class is a business logic or screen level state holder. It exposes state to the UI and encapsulates related business logic. Its principal advantage is that it caches state and persists it through configuration changes.
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) - A concurrency design pattern that you can use on Android to simplify code that executes asynchronously and it's the recommended way for asynchronous programming on Android.
- [Kotlin Flow](https://developer.android.com/kotlin/flow) - In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
- [Retrofit](https://square.github.io/retrofit) - Retrofit is a REST client for Java/ Kotlin and Android by Square. Its a simple network library that is used for network transactions.
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
- [JUnit](https://developer.android.com/training/testing/local-tests) - A widely used testing framework for Java, enabling the creation and execution of unit tests. It supports annotations, assertions, and test lifecycle management.
- [Mockk](https://github.com/mockk/mockk)- A mocking library for Kotlin
- [Coil](https://coil-kt.github.io/coil/compose/)- An image loading library for Android backed by Kotlin Coroutines.
- [Turbine](https://github.com/cashapp/turbine)- A small testing library for kotlinx.coroutines Flow
- [GSON](https://github.com/google/gson) - JSON Parser, used to parse requests on the data layer for Entities and understands Kotlin non-nullable and default parameters.
- [Logging Interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - An OkHttp interceptor which logs HTTP request and response data.
- [OkHttp](https://github.com/square/okhttp) - OkHttp is an HTTP client. It perseveres when the network is troublesome as it will silently recover from common connection problems.

## Reference Demo: App Functionality Steps
- Deny Location Permission: The video starts with the user denying the location permission when prompted.
- Manual Search: The user manually searches for a location despite the permission denial. 
- Open the App: The app opens and allows the user to search for a city without location access. 
- Allow Current Location: The user later decides to enable location access in the app settings. 
- Load Data for Current Location: After granting permission, the app loads data for the user’s current location.

https://github.com/pratikitsw/WeatherAndroidApp/tree/master/app/src/main/assets/WeatherAppDemo.mp4
