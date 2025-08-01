# AR Placement App for Android

A minimal Android application built using ARCore that allows users to select a drill and place a virtual marker (cube or cone) on a detected ground plane using augmented reality.

---

## 🎯 Objective

- Select a drill from a UI
- View drill-specific info
- Use AR to place a marker on real-world surfaces

---

## ✅ Features

### 1. Drill Selector

- List of 2–3 mock drills with:
  - Dummy image
  - Description
  - Tips
- Button to start AR drill mode

### 2. AR Drill Mode

- ARCore detects horizontal surfaces
- On tap: places a colored cube or cone
- Optional: restrict to one object at a time

---

## 🎥 Demo

Watch how the app works:  
👉 [Click here to view the video](https://drive.google.com/file/d/1Ij1_N6ZL9e94MIjJwQUSgOGRj3VzAfqJ/view?usp=sharing)

---

## 🖥️ UI Flow

### Drill Selection Page

```
---------------------------------------
|  Select Drill: [Dropdown/List]      |
|                                     |
|  [Start AR Drill]                   |
---------------------------------------
```

### AR View Page

- Live camera feed with AR overlay
- On-screen message: "Tap on ground to place drill marker"
- On tap → place a 3D cone or cube

---

## 📁 Project Structure

```
AR-Placement-App/
├── app/
│   ├── manifests/
│   ├── kotlin+java/
│   │   └── com/example/arplacementapp/
│   │       ├── MainActivity.kt              # App launcher
│   │       ├── ar/                          # AR functionality
│   │       ├── data/
│   │       │   └── Drill.kt                 # Data model
│   │       ├── navigation/
│   │       │   └── AppNavHost.kt           # Navigation setup
│   │       └── ui/
│   │           ├── DrillSelectionScreen.kt # Drill selector UI
│   │           ├── DrillDetailScreen.kt    # Drill detail UI
│   │           └── theme/
│   │               ├── Color.kt
│   │               ├── Theme.kt
│   │               └── Type.kt
│   ├── res/
│   │   ├── layout/
│   │   ├── drawable/
│   │   ├── mipmap/
│   │   └── values/
│   └── AndroidManifest.xml
├── build.gradle
└── README.md
```

---

## 💡 Tech Stack

- Kotlin  
- Android Studio  
- Jetpack Compose  
- ARCore SDK  

---

## ⚠️ Important Note

**This app only works on ARCore-supported devices.**  
Check if your device is supported:  
https://developers.google.com/ar/devices?authuser=2  

---

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/vaibhavporwal01/AR-Placement-App.git
   ```

2. Open the project in Android Studio.

3. Connect a physical Android device (ARCore-compatible).

4. Click "Run" to build and deploy.

5. Select a drill → Start AR Drill → Tap on the floor to place the marker.

---

## 📦 Deliverables

- Complete source code
- APK (build manually)
- AR functionality with drill placement
- Demo video showing working app

---

## 👨‍💻 Author

**Vaibhav Porwal**  
GitHub: [vaibhavporwal01](https://github.com/vaibhavporwal01)
