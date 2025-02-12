# NeverAgain [![](https://jitpack.io/v/tashilapathum/NeverAgain.svg)](https://jitpack.io/#tashilapathum/NeverAgain)
A simple dialog with a 'Don't show again' option to remember the preference. 


## Preview
| Customized dialog | With default labels | Dark mode |
| --- | --- | --- |
| ![Screenshot_20250212_213951](https://github.com/user-attachments/assets/12fdd74e-be37-476d-8429-d2a3794d7ce7) | ![Screenshot_20250212_214028](https://github.com/user-attachments/assets/a2528b41-457d-493c-815a-1bc947bdf16e) | ![Screenshot_20250212_214135](https://github.com/user-attachments/assets/b2ae30c8-8e43-4106-8918-3590ebb1a019) | 


## Features
- Stores the preference based on the hash value of the passed message
- Simply updating the message will show the dialog again with the new information
- Can reset a specific dialog or all dialogs
- Supports the latest Material 3 design
- Follows Dark and Light modes automatically
- Lightweight and Easy to implement

## Installation

Add JitPack to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        maven("https://jitpack.io")
    }
}
```

Then, add the dependency to your module-level `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.tashilapathum:NeverAgain:latest-version")
}
```

Replace `latest-version` with the latest release at the top of this file.

## Usage

### Minimal example

```kotlin
NeverAgainDialog
    .create("Tip", "This is a helpful tip!")
    .show(supportFragmentManager, "NeverAgainDialog")
```

### Full example 

```kotlin
NeverAgainDialog.create(
    title = "Enable Notifications",
    message = "Please allow notification permission to receive latest updates.",
    positiveButtonText = "OK",
    checkboxText = "Never show again",
    isChecked = false,
).setOnDismissedListener { isChecked ->
    if (isChecked) {
        // Handle user preference
    }
}.show(supportFragmentManager, "NeverAgainDialog")
```

### Clear preferences

```kotlin
private val tip1 = "This is a helpful tip."
NeverAgainDialog.create("Tip", tip1).show(supportFragmentManager, "NeverAgainDialog")
NeverAgainDialog().clear(context, tip1)
```
> Clears a specific dialog preference

```kotlin
NeverAgainDialog.create("Tip", tip1).show(supportFragmentManager, "NeverAgainDialog")
NeverAgainDialog().clearAll(context)
```
> Clears all dialog preferences

