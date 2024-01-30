package com.example.eisenhower

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val taskTitle = inputData.getString("taskTitle") ?: "Przypomnienie"

        val notification = NotificationCompat.Builder(applicationContext, "TASK_CHANNEL_ID")
            .setContentTitle("Przypomnienie")
            .setContentText(taskTitle)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure()
        }
        NotificationManagerCompat.from(applicationContext).notify(1, notification)

        return Result.success()
    }

    companion object {
        const val TASK_TITLE_KEY = "taskTitle"
    }
}
