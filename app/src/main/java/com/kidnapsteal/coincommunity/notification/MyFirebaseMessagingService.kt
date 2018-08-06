package com.kidnapsteal.coincommunity.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kidnapsteal.coincommunity.util.Ln

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val logTitle: String = javaClass.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
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
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
    }

    companion object {

        private val TAG = "MyFirebaseMsgService"
    }
}