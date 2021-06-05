package com.kazim.dictionaryapp.Receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kazim.dictionaryapp.MainMenu
import com.kazim.dictionaryapp.R
import android.text.format.DateFormat
import com.kazim.dictionaryapp.Service.AlarmService
import com.kazim.dictionaryapp.Util.Constants
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AlarmReceive","Alarm Received")
        val timeInMillis = intent?.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME,0L)

        when(intent.action){
            Constants.ACTION_SET_EXACT_ALARM -> {
                showPushNotification(context,intent)
            }

            Constants.ACTION_SET_REPETITIVE_ALARM -> {
                val cal = Calendar.getInstance().apply {
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)/2 // repeat twice a day
                }
                AlarmService(context).setRepetitiveAlarm(cal.timeInMillis)
                showPushNotification(context,intent)
            }
        }
    }

    fun showPushNotification(context: Context, intent: Intent){
        // Create an explicit intent for an Activity in your app


        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, "0")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Memorize your words!")
                .setContentText("Click to go to your word cards")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(0, builder.build())
        }
    }


    private fun convertDate(timeInMillis: Long): String =
            DateFormat.format("dd/MM/yyyy hh:mm:ss",timeInMillis).toString()

}