package com.kidnapsteal.coincommunity.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Job
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kidnapsteal.coincommunity.MainActivity
import com.kidnapsteal.coincommunity.R

import androidx.core.app.NotificationCompat
import com.kidnapsteal.coincommunity.util.Ln
import java.lang.Exception

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val logTitle: String = javaClass.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from!!)
        Ln.d(logTitle, "Receive Message : \nFrom - ${remoteMessage.from} " +
                "\nTo - ${remoteMessage.to}" +
                "\nID - ${remoteMessage.messageId}" +
                "\nData - ${remoteMessage.data}" +
                "\nTimestamp - ${remoteMessage.sentTime}" +
                "\nMessageType - ${remoteMessage.messageType}" +
                "\nNotification - ${remoteMessage.notification}" +
                "\nTitle - ${remoteMessage.notification?.title}" +
                "\nbody - ${remoteMessage.notification?.body}" +
                "\ntag- ${remoteMessage.notification?.tag}" +
                "\ncolor- ${remoteMessage.notification?.color}" +
                "\nicon- ${remoteMessage.notification?.icon}" +
                "\nclickAction- ${remoteMessage.notification?.clickAction}")
    }

    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token: " + token!!)
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
    }

    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }


    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        val myJob = dispatcher.newJobBuilder()
                .setService(MyJobService::class.java)
                .setTag("my-job-tag")
                .build()
        dispatcher.schedule(myJob)
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    override fun onMessageSent(p0: String?) {
        super.onMessageSent(p0)
        Ln.d("onMessageSent ID :$p0 ")
    }

    override fun onSendError(p0: String?, p1: Exception?) {
        super.onSendError(p0, p1)
        Ln.d("onMessageSent ID :$p0 \nMessage: ${p1?.message}")

    }

    companion object {

        private val TAG = "MyFirebaseMsgService"
    }
}