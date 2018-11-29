package receiver.command.zsb.com.smscommandreceiver.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

/**
 * @author Zsolt Bokor
 * @since 29/11/2018.
 */
class SMSBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != null && intent.action == SMS_RECEIVED) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<*>
                val messages = arrayOfNulls<SmsMessage>(pdus.size)
                for (i in pdus.indices) {
                    messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                }

                parseAndExecute(context, messages[0]!!.messageBody)
            }
        }
    }

    private fun parseAndExecute(context: Context, message: String?) {
        Log.d(TAG, "message=" + message!!)

        if (message.isEmpty()) return

        val explode = message.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (explode[0] != PrefUtils.getPassword(context)) return

        if (explode.size == 2) {
            if (Command.TYPE_TURN_ON_WIFI == explode[1]) {
                Utils.enableWifi(context)
                return
            }

            if (Command.TYPE_TURN_OFF_WIFI == explode[1]) {
                Utils.disableWifi(context)
                return
            }

            if (Command.TYPE_WAKE_UP == explode[1]) {
                Utils.wakeUp(context)
                return
            }
        }

        if (explode.size == 3) {
            if (Command.TYPE_OPEN_APP == explode[1]) {
                Utils.openApplicationByName(context, explode[2])
            }
        }
    }

    companion object {

        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
        private const val TAG = "SMSBroadcastReceiver"
    }
}
