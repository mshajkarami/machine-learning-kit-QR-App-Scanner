# 📷 machine-learning-kit-QR-App-Scanner

A powerful and modern Android QR code scanner built with **CameraX** and **ML Kit** by Google.  
This app analyzes real-time camera frames to detect and read **QR codes** and **Aztec codes** with high accuracy and speed.

---

## 🚀 Features

- 📸 Real-time camera stream using **CameraX**
- 🤖 Barcode & QR detection using **ML Kit**
- 🧠 Supports **QR codes** and **Aztec codes**
- 🔒 Auto-detects Wi-Fi QR codes (SSID & password)
- 🌐 Recognizes URLs and displays them in a bottom sheet dialog
- ⚡ Fast scanning with minimal resource usage
- ✅ Prevents multiple scans per session (one-time scan lock)
- 💡 Easy to extend for more barcode types
---

## 🧠 Tech Stack

| Layer            | Technology             |
|------------------|------------------------|
| UI               | Kotlin / Java (XML)    |
| Camera           | CameraX                |
| Machine Learning | ML Kit (Barcode API)   |
| Architecture     | MVVM (if applicable)   |
| Min SDK          | 21+                    |

---

## 🔧 Setup Instructions

1. Clone the repository:

```bash
git clone https://github.com/mshajkarami/machine-learning-kit-QR-App-Scanner.git

2.Open the project in Android Studio.

Make sure the following dependencies are added in build.gradle:
implementation 'com.google.mlkit:barcode-scanning:17.2.0'
implementation 'androidx.camera:camera-camera2:1.1.0'
implementation 'androidx.camera:camera-lifecycle:1.1.0'
implementation 'androidx.camera:camera-view:1.0.0Run the app on a real device (CameraX may not work properly on emulator).

3.Run the app on a real device (CameraX may not work properly on emulator).
📂 Project Structure
├── app/
│   ├── java/
│   │   └── .../
│   │       ├── MainActivity.java
│   │       ├── MyImageAnalyzer.java
│   │       └── BottomDialog.java
│   └── res/
│       ├── layout/
│       └── drawable/

✅ How it works
The camera feed is continuously analyzed using ImageAnalysis.
Every frame is passed to ML Kit for barcode detection.
Once a QR or Aztec code is found, it's parsed and shown using a custom BottomDialog.
To prevent duplicate scans, a lock (isScanned) is used.
The app can detect:
Wi-Fi info (SSID, password, encryption type)
URLs
Any other raw text codes

🎯 Future Improvements
 Add support for more barcode types (PDF417, Code 128, etc.)
 Open scanned URLs directly in browser
 Add dark/light theme toggle
 Show scan history with timestamps

🧑‍💻 Author
Mohamad Saleh HajKarami
🎓 Computer Engineering Student
📍 Mohajer University of Isfahan
🌐 GitHub Profile

📄 License
MIT License — feel free to use, modify, and share this project!
