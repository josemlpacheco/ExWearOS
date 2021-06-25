package com.example.mobileapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat

class NotificationDetailsActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notification_details)

    val sequence: CharSequence? = getMessageText(intent)
    val stringReply: String = sequence!!.toString()

    processMessage(stringReply)
  }

  private fun getMessageText(intent: Intent): CharSequence? {
    val remoteInput: Bundle? = RemoteInput.getResultsFromIntent(intent)

    return remoteInput?.getCharSequence(NotificationUtils.extraVoiceReply)
  }

  private fun processMessage(message: String) {
    if (message.equals("SMS", true)) {
      sendSMS("SMS","2381808326")
    }
  }

  private fun sendSMS(message: String, destination: String) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
      val manager: SmsManager = SmsManager.getDefault()
      manager.sendTextMessage(destination, null, message, null, null)
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 0)
      }
    }
  }
}