package receiver.command.zsb.com.smscommandreceiver.receiver

import android.content.Context
import android.preference.PreferenceManager

/**
 * @author Zsolt Bokor
 * @since 29/11/2018.
 */
object PrefUtils {

    private const val PASSWORD = "password"

    fun getPassword(context: Context): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(PASSWORD, null)
    }

    fun storePassword(context: Context, password: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()

        editor.putString(PASSWORD, password)
        editor.apply()
    }

}