package receiver.command.zsb.com.smscommandreceiver

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import receiver.command.zsb.com.smscommandreceiver.receiver.PrefUtils

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.INTERNET
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    1
                )
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECEIVE_SMS
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.RECEIVE_SMS
                    )
                ) {
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.RECEIVE_SMS),
                        1
                    )
                }
            } else {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WAKE_LOCK
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WAKE_LOCK
                        )
                    ) {
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.WAKE_LOCK),
                            1
                        )
                    }
                } else {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.DISABLE_KEYGUARD
                        )
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.DISABLE_KEYGUARD
                            )
                        ) {
                        } else {
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.DISABLE_KEYGUARD),
                                1
                            )
                        }
                    } else {

                    }
                }
            }
        }

        val editText: EditText = findViewById(R.id.password_value)
        val applyButton: Button = findViewById(R.id.save_button)

        applyButton.setOnClickListener {
            if (editText.text.isNullOrEmpty()) {
                return@setOnClickListener
            }

            PrefUtils.storePassword(this, editText.text.toString())
            editText.text.clear()

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
