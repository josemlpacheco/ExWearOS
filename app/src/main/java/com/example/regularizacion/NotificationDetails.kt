package com.example.regularizacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.Toast
import androidx.core.app.RemoteInput

class NotificationDetails : WearableActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notification_details)

    //Message
    val sequence: CharSequence? = getMessageText(intent)
    val replyWas: String = getString(R.string.reply_was)
    Toast.makeText(this, replyWas + sequence!!, Toast.LENGTH_LONG).show()
  }

  private fun getMessageText(intent: Intent): CharSequence? {
    val remoteInput: Bundle? = RemoteInput.getResultsFromIntent(intent)

    return remoteInput?.getCharSequence(NotificationUtils.extraVoiceReply)
  }
}