package com.example.notificationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // Channel ID for notifications
    private val CHANNEL_ID = "channel_id_example_01"
    private val notification_id = 101

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming FCM messages here and create local notifications.

        // Extract notification data from the FCM message
        val title = remoteMessage.notification?.title
        val message = remoteMessage.notification?.body

        // Check if title and message are not null
        if (title != null && message != null) {
            // Create a notification channel (if not already created)
            createNotificationChannel()

            // Build the notification
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // Display the notification
            with(NotificationManagerCompat.from(this)) {
                notify(notification_id, builder.build())
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
