# **📱 Social Learning App – Capstone Project**

> **Final Internship Project**
> A comprehensive social learning platform integrating **Quizzes, Tasks, Chat, Ads, and Firebase** to provide a seamless and engaging experience for users.

---

## **🌟 Overview**

The **Social Learning App** is a **feature-packed Android application** built to combine **learning, productivity, and community** in one platform.
It leverages **Firebase for real-time features**, **Jetpack Compose for modern UI**, and **AdMob for monetization**.

---

## **🎯 Objectives**

* Integrate **UI state management** with **Jetpack Compose**.
* Manage **Firebase Authentication** and **Realtime Database** for dynamic data.
* Provide **real-time chat** and **task synchronization**.
* Monetize with **AdMob ads** in a clean, non-intrusive way.
* Deliver a **scalable, modular codebase** with best practices.

---

## **🛠️ Tech Stack**

| **Category**       | **Technologies**                                     |
| ------------------ | ---------------------------------------------------- |
| **Language**       | Java + Kotlin (for Compose UI)                       |
| **UI**             | Jetpack Compose, Material 3                          |
| **Database**       | Firebase Realtime Database                           |
| **Authentication** | Firebase Auth (Email/Password)                       |
| **Ads**            | Google AdMob (Banner + Interstitial)                 |
| **Navigation**     | Jetpack Compose Navigation  /XML                     |
| **Other Tools**    | SharedPreferences



## **✨ Features**

### **1️⃣ Authentication & Onboarding** *(Week 3 & 4)*

* Firebase **Login & Register** (email/password).
* Store **user profiles** (name, email, avatar URL) in Firebase.
* **3-screen onboarding flow**:

  * **Screen 1:** Welcome to the app.
  * **Screen 2:** Overview of features.
  * **Screen 3:** Privacy & Terms with “Finish” button.
* **Banner Ads** in onboarding screens.
* **Interstitial Ad** shown on completion of onboarding.

---

### **2️⃣ Quiz Module** *(Week 1)*

* **5 MCQ questions** with **10-second timer** per question.
* **Auto-skip** unanswered questions when timer ends.
* Final **scoreboard** at the end.
* **Save scores** under each user profile in Firebase.
* **Quiz history** with all past attempts.

---

### **3️⃣ Task Manager Module** *(Week 2)*

* Add, **edit**, and **delete** tasks.
* Tasks include:

  * **Title, description, priority, status**.
* **Filters**: All / Pending / Done.
* Real-time sync with Firebase per user.
* Multi-device consistency.

---

### **4️⃣ Chat Module** *(Week 5)*

* **Real-time group chat** using Firebase Database.
* Send **text messages**. *(Bonus: image sharing if time permits)*
* **Online/Offline user status** (optional).

---

### **5️⃣ App Navigation**

* Bottom navigation with **tabs for**:

  * Quiz
  * Tasks
  * Chat
  * Profile
* **Profile Screen** shows:

  * Name, email
  * Quiz history (count & attempts)
  * Task count
--------------------------------------------------------------
 Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/).
2. Create a new project.
3. Enable:

   * Authentication (Email/Password)
   * Realtime Database
4. Download `google-services.json` and place it in `app/`.

 AdMob Setup

1. Go to [AdMob](https://admob.google.com/).
2. Create an app and generate **Ad IDs**:
   * Banner Ad
   * Interstitial Ad
3. Replace test IDs in `AdsConfig.java` with your production IDs.
