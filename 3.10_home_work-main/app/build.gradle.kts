plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "ru.samsung.itschool.a311_practical_work"
	compileSdk = 36

	defaultConfig {
		applicationId = "ru.samsung.itschool.a311_practical_work"
		minSdk = 24
		targetSdk = 36
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.17.0")
	implementation("androidx.appcompat:appcompat:1.7.1")
	implementation("com.google.android.material:material:1.13.0")
	implementation("androidx.activity:activity:1.12.0")
	implementation("androidx.navigation:navigation-fragment-ktx:2.9.6")
	implementation("androidx.constraintlayout:constraintlayout:2.2.1")
	implementation("androidx.navigation:navigation-ui-ktx:2.9.6")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
	implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.3.0")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
}