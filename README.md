# Notes App

This is a simple Android notes app built using Jetpack Compose, Room for local storage, and MVVM architecture. The app allows users to manage notes with various types (e.g., work, sport, personal) and includes basic CRUD functionality (Create, Read, Update, Delete).

## Features

- **Add Notes**: Users can create new notes with a title, content, type (e.g., work, sport), and mark them as "done".
- **Edit Notes**: Users can update existing notes, modifying any of the fields.
- **Delete Notes**: Users can delete notes they no longer need.
- **Filter Notes by Type**: Users can filter notes by type to display only relevant categories (e.g., work, personal).
- **Mark as Done**: Users can mark notes as done, which visually changes the background color of the note.

## Architecture

- **MVVM** (Model-View-ViewModel): 
  - **Model**: Contains data and methods for interacting with the database.
  - **ViewModel**: Contains business logic and transforms data into a form suitable for the UI.
  - **View**: UI components (Jetpack Compose) that observe the ViewModel and update based on changes.

- **Room**: A local database to store notes.
- **Jetpack Compose**: For building the UI using declarative Kotlin code.

## Getting Started

### Prerequisites

To build this app, you need to have the following installed:

- [Android Studio](https://developer.android.com/studio) (latest stable version)
- JDK 11 or newer

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/notes-app.git
    cd notes-app
    ```

2. Open the project in Android Studio.

3. Sync the project with Gradle files.

4. Build and run the app on an emulator or physical device.

### Dependencies

This project uses the following dependencies:

- **Jetpack Compose** for building the UI:
  - `androidx.compose.ui:ui`
  - `androidx.compose.material3:material3`
  - `androidx.compose.foundation:foundation`
  
- **Room** for local database management:
  - `androidx.room:room-runtime`
  - `androidx.room:room-compiler`

- **Lifecycle** components for ViewModel and LiveData:
  - `androidx.lifecycle:lifecycle-runtime-ktx`
  - `androidx.lifecycle:lifecycle-viewmodel-ktx`

## Usage

1. **Add a Note**: Click the "Add" button to open a dialog where you can enter a title, content, and type for the new note. You can also mark it as "done".
2. **Edit a Note**: Expand a note to view its details, then click the "Edit" icon to modify the note.
3. **Delete a Note**: Click the "Delete" icon next to a note to remove it.
4. **Filter Notes**: Use the filter buttons at the bottom to view notes based on their type (e.g., work, sport, all).

## Screenshots

[![App Screenshot](screenshots/screenshot1.png)](https://github.com/Razmik2001/Notes/blob/master/Screenshot%20from%202025-04-04%2021-46-28.png)
[![App Screenshot](screenshots/screenshot2.png)](https://github.com/Razmik2001/Notes/blob/master/Screenshot%20from%202025-04-04%2021-46-38.png)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

If you find any issues or want to contribute to this project, feel free to open an issue or submit a pull request!

---

### Acknowledgements

- [Jetpack Compose](https://developer.android.com/jetpack/compose) for making UI development simpler.
- [Room](https://developer.android.com/topic/libraries/architecture/room) for local database management.
- [Kotlin](https://kotlinlang.org/) for its concise syntax and features.

