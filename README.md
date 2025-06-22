# [Hash Talk]

A micro social media app for Android built with Clean Architecture.

## Features

- User authentication (sign up/login/logout)
- Create posts with text/hash/images
- Timeline displaying all user posts

## Tech Features

- Kotlin + Jetpack Compose
- MVVM and Clean Architecture
- Feature-based package structure
- Error handling class
- Ready for type-safe Navigation
- Nav graphs
- UI-models/Domain-models
- Hilt for DI
- Firebase (Auth, Firestore, Storage)

## Setup

1. Clone the repository
2. Add your `google-services.json` file to `app/` directory
3. Open in Android Studio and run

## Firebase Setup

- Create Firebase project
- Enable Authentication (Email/Password)
- Enable Firestore Database
- Enable Firebase Storage

## Testing

Tests cover authentication, post creation, image upload, and timeline functionality.

## TODO

A few improvements that could be included in the future.

### Authentication

- [ ] Password strength meter with visual feedback for better UX
- [ ] Separate error messages for login vs signup 
- [ ] Auth state Firebase error handling implementation

### Media & File Handling

- [ ] File extension validation for image uploads
- [ ] Image compression before upload

### User Experience

- [ ] Improved navigation flow - distinguish between:
    - AddPost -> Back Navigation
    - AddPost -> Timeline
    
    When navigating back we want to preserve timeline list position, but when creating a new post we want see our new post.
- [ ] Snackbars for various states. 
- [ ] Swipe to refresh/retry in Timeline.
- [ ] Refactor to use a single scaffold.
- [ ] Display loading state while image is loading in Timeline.
- [ ] Error handling for logout. 
- [ ] Catch InvalidCredentials from Firebase. 

### Performance & Optimization

- [ ] Enhanced error handling for network failures
- [ ] Loading states for better user feedback

### CI/CD

- [ ] Add .yml file and setup GitHub Actions flow for commits, PR and artifact creation.
