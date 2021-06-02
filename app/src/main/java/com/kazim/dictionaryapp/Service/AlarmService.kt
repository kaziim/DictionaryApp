package com.kazim.dictionaryapp.Service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.kazim.dictionaryapp.Receiver.AlarmReceiver
import com.kazim.dictionaryapp.Util.Constants
import com.kazim.dictionaryapp.Util.RandomIntUtil

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    var pendingIntent: PendingIntent ?= null

    fun setExactAlarm(timeInMillis: Long){
        pendingIntent = getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_EXACT_ALARM
                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME,timeInMillis)
                }
        )
        setAlarm(
                timeInMillis,
                getPendingIntent(
                        getIntent().apply {
                            action = Constants.ACTION_SET_EXACT_ALARM
                            putExtra(Constants.EXTRA_EXACT_ALARM_TIME,timeInMillis)
                        }
                )
        )
    }

    //Every day
    fun setRepetitiveAlarm(timeInMillis: Long){
        setAlarm(
                timeInMillis,
                getPendingIntent(
                        getIntent().apply {
                            action = Constants.ACTION_SET_REPETITIVE_ALARM
                            putExtra(Constants.EXTRA_EXACT_ALARM_TIME,timeInMillis)
                        }
                )
        )
    }

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent){
        alarmManager?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        timeInMillis,
                        pendingIntent
                )
            }else{
                AlarmManager.RTC_WAKEUP
                timeInMillis
                pendingIntent
            }
        }
    }

    fun cancelAlarm(){
        pendingIntent?.let {
            alarmManager?.cancel(it)
        }

    }

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntent(intent: Intent) =
            PendingIntent.getBroadcast(
                    context,
                    RandomIntUtil.getRandomInt(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
}