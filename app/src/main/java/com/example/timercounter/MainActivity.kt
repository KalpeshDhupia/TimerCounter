package com.example.timercounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var data: String
    lateinit var Pdata: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSubmit.setOnClickListener {
            data = etData.text.toString()
            var inputTime = data.toLong()
            if (inputTime < 11) {
                Toast.makeText(this, "Notification shown in $inputTime Min", Toast.LENGTH_SHORT)
                    .show()
                startWork(inputTime)
            } else {
                Toast.makeText(this, "Enter Valid data", Toast.LENGTH_SHORT).show()
            }
        }
        btnPSubmit.setOnClickListener {
            Pdata = etPData.text.toString()
            var inputPTime = Pdata.toLong()
            if (inputPTime > 14) {
                Toast.makeText(this, "Notification shown in $inputPTime Min", Toast.LENGTH_SHORT)
                    .show()
                startPeriodicWork(inputPTime)
            } else {
                Toast.makeText(this, "Enter Valid data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startPeriodicWork(inputPTime: Long) {
        val constraints = Constraints.Builder()
        constraints.setRequiresCharging(true)

        val periodicWork =
            PeriodicWorkRequest.Builder(PeriodicWorkerClass::class.java, 15, TimeUnit.MINUTES)
        periodicWork.setConstraints(constraints.build())
        periodicWork.setInitialDelay(inputPTime, TimeUnit.MINUTES)
            .build()


        val workManager = WorkManager.getInstance(this)
        workManager.enqueue(periodicWork.build())

    }


    private fun startWork(inputTime: Long) {

        val constraints = Constraints.Builder()
        constraints.setRequiresCharging(true)

        val oneTimeWork = OneTimeWorkRequest.Builder(OneTimeWorkerClass::class.java)
        oneTimeWork.setConstraints(constraints.build())
        oneTimeWork.setInitialDelay(inputTime, TimeUnit.MINUTES)
            .build()


        val workManager = WorkManager.getInstance(this)
        workManager.enqueue(oneTimeWork.build())

    }
}