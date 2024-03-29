import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.moko.resources)
    alias(libs.plugins.buildConfig)
}

project.version = "1.0.0"

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.majorVersion
            }
        }
    }

    jvm("desktop")

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = project.version.toString()
        summary = "CompCardero is a simple card game built with Kotlin Multiplatform and Compose"
        homepage = "https://github.com/dbaelz/CompCardero"
        ios.deploymentTarget = "11.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(libs.material.icons.core)
                implementation(libs.material.icons.extended)
                implementation(libs.moko.resources)
                implementation(libs.moko.resources.compose)
                implementation(libs.voyager.navigator)
                implementation(libs.composeImageLoader)
                implementation(libs.napier)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.multiplatformSettings)
                implementation(libs.multiplatformSettingsNoArg)
                implementation(libs.multiplatformSettingsSerialization)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activityCompose)
                implementation(libs.compose.uitooling)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.koin.android)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
            }
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "de.dbaelz.compcardero"
    compileSdk = 34

    defaultConfig {
        minSdk = 28
        targetSdk = 34

        applicationId = "de.dbaelz.compcardero"
        versionCode = 1
        versionName = project.version.toString()
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain(11)
    }

    packagingOptions {
        resources.excludes.add("META-INF/**")
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "CompCardero"
            packageVersion = project.version.toString()
            macOS {
                iconFile.set(project.file("package_icon/icon.icns"))
            }
            windows {
                iconFile.set(project.file("package_icon/icon.ico"))
            }
            linux {
                iconFile.set(project.file("package_icon/icon.png"))
            }
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "de.dbaelz.compcardero"
}

buildConfig {
    packageName("de.dbaelz.compcardero.config")

    buildConfigField("String", "APP_VERSION", provider { "\"${project.version}\"" })
}
