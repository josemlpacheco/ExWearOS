package com.example.regularizacion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.Wearable

class MainActivity : WearableActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Enables Always-on
    setAmbientEnabled()
  }
  //This function allows send an message to wear OS
  fun voiceInput(view: View) {
    val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val NOTIFICATION_ID = "my_channel_id_01"

    val intent = Intent(this, NotificationDetails::class.java)
    val pendingIntent = PendingIntent.getActivities(this, 0, arrayOf(intent), PendingIntent.FLAG_UPDATE_CURRENT)

    //Information about notification
    val replyLabel: String = getString(R.string.reply_label)
    val replyChoices: Array<String> = resources.getStringArray(R.array.reply_choices)
    val remoteInput: RemoteInput = RemoteInput.Builder(NotificationUtils.extraVoiceReply)
      .setLabel(replyLabel)
      .setChoices(replyChoices)
      .build()
    val notificationCompatBuilder: NotificationCompat.Action.Builder = NotificationCompat
      .Action.Builder(R.drawable.ic_expand_more_white_22, "Notification", pendingIntent)

    notificationCompatBuilder.addRemoteInput(remoteInput)
    val action: NotificationCompat.Action = notificationCompatBuilder.build()


    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
      val notificationChannel = NotificationChannel(NOTIFICATION_ID, "This is a notification", NotificationManager.IMPORTANCE_HIGH)
      notificationChannel.description = "Channel description"
      notificationChannel.enableLights(true)
      notificationChannel.lightColor = Color.GREEN
      notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
      notificationChannel.enableVibration(true)
      notificationManager.createNotificationChannel(notificationChannel)
    }

    //Notification
    val builder = NotificationCompat.Builder(this, NOTIFICATION_ID)
    builder.setAutoCancel(true)
      .setSmallIcon(R.drawable.ic_full_sad)
      .setContentTitle("Notification")
      .setContentText("This is a notification")
      .setContentInfo("Information")
      .setContentIntent(pendingIntent)
      .addAction(action)
      .extend(NotificationCompat.WearableExtender().setContentAction(0))

    notificationManager.notify(1, builder.build())
  }
}