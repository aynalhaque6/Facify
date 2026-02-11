# 🔐 Face Verification App
### Facify Face Liveness SDK Integration (Android)

---

## 📌 Overview

This project demonstrates professional integration of the **Facify Face Liveness SDK** in an Android application.

It includes:

- ✔ MainActivity (Launch screen)
- ✔ VerificationActivity (FacifyView integration)
- ✔ Camera permission handling
- ✔ Customizable liveness configuration
- ✔ Success/Error callback handling

---

## 🏗 Project Structure

com.example.facedetection
│
├── MainActivity.java
├── VerificationActivity.java
│
res/layout
├── activity_main.xml
└── activity_verification.xml

---

## 🚀 How It Works

### 1️⃣ MainActivity

Launches the built-in Face Liveness screen:

```java
startActivity(new Intent(MainActivity.this, FaceLivenessActivity.class));
```

---

### 2️⃣ VerificationActivity (Advanced Control)

Configure liveness flow:

```java
FacifyConfig config = new FacifyConfig.Builder()
        .setTotalSteps(6)
        .setShowDots(true)
        .setShowStatusText(true)
        .setShowSpinner(true)
        .setShowCloseButton(true)
        .setRingStyle(FacifyConfig.RingStyle.DOTS)
        .setAutoRestartOnFail(true)
        .setRestartDelayMs(700)
        .setStepTimeoutMs(12000)
        .setDesign(FacifyDesign.FACIFY_DESIGN)
        .build();
```

Start verification:

```java
facifyView.start(this, config, new FacifyCallback() { ... });
```

---

## 🎯 SDK Callbacks

| Callback | Description |
|----------|-------------|
| onPermissionRequired | Request camera permission |
| onStateChanged | Track liveness progress |
| onSuccess | Returns verified image path |
| onError | Called when verification fails |
| onCancelled | User cancelled process |

---

## 📷 Layout Usage

```xml
<com.aynal.facify.sdk.FacifyView
    android:id="@+id/facifyView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

---

## 📦 Requirements

- Android 6.0+
- Camera Permission
- CameraX
- Google ML Kit

---

## 🔒 Security

This project uses:

- R8 / ProGuard optimization
- Log stripping in release
- Public API protection
- CameraX & ML Kit keep rules

---

## 👨‍💻 Author

**Md Aynal Haque**  
Android Developer  
Bangladesh 🇧🇩

---

## 📄 License

This project demonstrates integration of a proprietary Face Liveness SDK.  
Unauthorized redistribution is prohibited.
