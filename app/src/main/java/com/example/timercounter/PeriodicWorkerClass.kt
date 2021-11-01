package com.example.timercounter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class PeriodicWorkerClass(val appContext: Context, val workerParams: WorkerParameters) :
    Worker(appContext, workerParams)  {
    override fun doWork(): Result {
        displayNotification("PeriodicWork","Work")
        return Result.success()

    }
    private fun displayNotification(task: String, desc: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "workExample",
                "workExample",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val builder = NotificationCompat.Builder(applicationContext, "workExample")
            .setContentTitle(task)
            .setContentText(desc)
            .setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(1, builder.build())
    }

}