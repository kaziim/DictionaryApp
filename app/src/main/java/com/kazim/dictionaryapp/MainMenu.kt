package com.kazim.dictionaryapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kazim.dictionaryapp.MainFragments.QuizFragment
import com.kazim.dictionaryapp.Service.AlarmService
import java.util.*


class MainMenu : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "0"
    private val description = "Test notification"

    private var score: Int = 0

    public lateinit var alarmService: AlarmService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)

        bottomNavigationView.setupWithNavController(navController)

        alarmService = AlarmService(this)

        createNotificationChannel()


        getScore() //get last score from the quiz activity & send it to quiz fragment


    }


    public fun setAlarm(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            DatePickerDialog(
                    this@MainMenu,
                    0,
                    { _, year, month, dayOfMonth ->
                        this.set(Calendar.YEAR, year)
                        this.set(Calendar.MONTH, month)
                        this.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        TimePickerDialog(
                                this@MainMenu,
                                0,
                                { _, hourOfDay, minute ->
                                    this.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                    this.set(Calendar.MINUTE, minute)
                                    callback(this.timeInMillis)
                                },
                                this.get(Calendar.HOUR_OF_DAY),
                                this.get(Calendar.MINUTE),
                                false
                        ).show()
                    },
                    this.get(Calendar.YEAR),
                    this.get(Calendar.MONTH),
                    this.get(Calendar.DAY_OF_MONTH),

                    ).show()
        }
    }


    public fun cancelAlarm() {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val myIntent = Intent(this, MainMenu::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    public fun showPushNotification() {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, MainMenu::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(0, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Dictionary App"
            val descriptionText = description
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getScore() {
        score = getIntent().getIntExtra("score", 0);
        //Toast.makeText(this,"score is : $score", Toast.LENGTH_LONG).show()

        //Toast.makeText(this,quizType,Toast.LENGTH_SHORT).show()

        val bundle = Bundle()
        bundle.putInt("score", score)
        // set Fragmentclass Arguments
        val fragobj = QuizFragment()
        fragobj.setArguments(bundle)
    }


}