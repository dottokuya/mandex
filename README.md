
# MangaDex Web Wrapper for Android

Web wrapper of MangaDex to work as an Android app. You can apply this wrapper to your web app too.

## Features
- Wrap the web app inside a single WebView and act like a standalone app (assume your web app is already a SPA/PWA/functional mobile-friendly website)
- Swipe to refresh
- Receive the web app URL from other applications and open in the app
- (currently planning) Push notification (assume your app support APIs)

## Apply this wrapper to your web app
- Clone this repository
- Change the package name to your liking in AndroidManifest.xml and files in `java` folder
- Change the URL to your app
  + In AndroidManifest.xml
    ```<activity
            android:name=".MainActivity"
            android:launchMode="singleTop">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data
                    android:scheme="https"
                    android:host="*Insert your URL here" />
            </intent-filter>
        </activity>```
  + In MainActivity.java
    `string defaultUrl = "*Insert your URL here with HTTP prefix*";`

