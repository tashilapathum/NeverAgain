# NeverAgain [![](https://jitpack.io/v/tashilapathum/NeverAgain.svg)](https://jitpack.io/#tashilapathum/NeverAgain)
A simple Material Design dialog with a 'Don't show again' option to remember the preference. 

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

#### If you're using version catalog:

1. Add these to `libs.versions.toml`
```kotlin
neveragain = { module = "com.github.tashilapathum:NeverAgain", version.ref = "latest-version" }
```

2. Add this to module-level `build.gradle.kts`:
```kotlin
implementation(libs.neveragain)
```

Replace `latest-version` with the latest release at the top of this file.

## Usage

### Minimal Example

```kotlin
NeverAgainDialog.create("Tip", "This is a helpful tip!").show(supportFragmentManager, "NeverAgainDialog")
```

### Full Example 

```kotlin
NeverAgainDialog.create(
    title = "Enable Notifications",
    message = "Would you like to receive notifications?",
    checkboxText = "Never show again",
    isChecked = false,
    positiveButtonText = "OK"
).setOnDismissedListener { isChecked ->
    if (isChecked) {
        // Handle user preference
    }
}.show(supportFragmentManager, "NeverAgainDialog")
```


