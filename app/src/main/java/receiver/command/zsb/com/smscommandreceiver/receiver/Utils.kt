package receiver.command.zsb.com.smscommandreceiver.receiver

import android.annotation.SuppressLint
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.PowerManager

/**
 * @author Zsolt Bokor
 * @since 29/11/2018.
 */
internal object Utils {

    fun openApplicationByName(context: Context, appName: String) {
        val packageManager = context.packageManager
        val apps = packageManager.getInstalledPackages(0)
        for (app in apps) {
            try {
                val lAppName = packageManager
                    .getApplicationLabel(
                        packageManager.getApplicationInfo(app.packageName, PackageManager.GET_META_DATA)
                    ).toString()

                if (appName.equals(lAppName, ignoreCase = true)) {
                    openApplication(context, app.packageName)
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun openApplication(context: Context, applicationId: String) {
        val pm = context.packageManager

        val intent = pm.getLaunchIntentForPackage(applicationId)
        // Add category to intent
        if (intent != null) {
            intent.addCategory(Intent.CATEGORY_LAUNCHER)

            // Start the app
            context.startActivity(intent)
        }
    }

    fun enableWifi(context: Context) {
        val wifiManager: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = true
    }

    fun disableWifi(context: Context) {
        val wifiManager: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = false
    }

    @SuppressLint("ObsoleteSdkInt")
    fun wakeUp(context: Context) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val result =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH && powerManager.isInteractive
                    || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH && powerManager.isScreenOn

        if (!result) {
            @SuppressLint("InvalidWakeLockTag") val wl = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
                "MH24_SCREENLOCK"
            )
            wl.acquire(10000)
            @SuppressLint("InvalidWakeLockTag") val wl_cpu =
                powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MH24_SCREENLOCK")
            wl_cpu.acquire(10000)
        }
    }
}
