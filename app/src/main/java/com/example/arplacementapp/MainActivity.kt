package com.example.arplacementapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.arplacementapp.ar.ArPlacementActivity
import com.example.arplacementapp.navigation.AppNavHost
import com.example.arplacementapp.ui.theme.ARPlacementAppTheme
import com.google.ar.core.ArCoreApk
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException

class MainActivity : ComponentActivity() {

    private var mUserRequestedInstall = true
    private var currentDrillId: String? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            checkArCoreAvailability()
        } else {
            Toast.makeText(this, "Camera permission is required for AR.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARPlacementAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        onStartArActivity = { drillId ->
                            this.currentDrillId = drillId // Store the drillId
                            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (mUserRequestedInstall || currentDrillId != null) {
            checkArCoreAvailability()
        }
    }

    private fun checkArCoreAvailability() {
        if (currentDrillId == null && mUserRequestedInstall) {
            //Logic could be built later when need user to install AR core
        }


        val availability: ArCoreApk.Availability
        try {
            availability = ArCoreApk.getInstance().checkAvailability(this)
            if (availability == ArCoreApk.Availability.UNKNOWN_CHECKING) {
                return
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error checking ARCore availability: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            return
        }

        when (availability) {
            ArCoreApk.Availability.SUPPORTED_INSTALLED -> {
                if (currentDrillId != null) {
                    startArActivity()
                }
            }
            ArCoreApk.Availability.SUPPORTED_APK_TOO_OLD,
            ArCoreApk.Availability.SUPPORTED_NOT_INSTALLED -> {
                try {
                    val installStatus = ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)
                    when (installStatus) {
                        ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                            mUserRequestedInstall = false
                            return
                        }
                        ArCoreApk.InstallStatus.INSTALLED -> {
                            checkArCoreAvailability() // Re-check to confirm and then start
                        }
                    }
                } catch (e: UnavailableUserDeclinedInstallationException) {
                    Toast.makeText(this, "ARCore installation was declined.", Toast.LENGTH_LONG).show()
                    currentDrillId = null
                } catch (e: Exception) {
                    Toast.makeText(this, "Failed to request ARCore installation: ${e.message}", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                    currentDrillId = null
                }
            }
            ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE -> {
                Toast.makeText(this, "This device does not support AR.", Toast.LENGTH_LONG).show()
                currentDrillId = null
            }
            else -> {
                Toast.makeText(this, "ARCore is not supported or ready (State: $availability).", Toast.LENGTH_LONG).show()
                currentDrillId = null
            }
        }
    }

    private fun startArActivity() {
        currentDrillId?.let { drillId ->
            if (ArCoreApk.getInstance().checkAvailability(this) == ArCoreApk.Availability.SUPPORTED_INSTALLED) {
                val intent = Intent(this, ArPlacementActivity::class.java)
                intent.putExtra("DRILL_ID_KEY", drillId)
                startActivity(intent)
                this.currentDrillId = null
                mUserRequestedInstall = true
            } else {
                Toast.makeText(this, "ARCore not ready to start.", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this, "Drill ID not available to start AR.", Toast.LENGTH_SHORT).show()
        }
    }
}
