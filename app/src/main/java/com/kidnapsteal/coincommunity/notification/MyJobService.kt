package com.kidnapsteal.coincommunity.notification

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.kidnapsteal.coincommunity.util.Ln

class MyJobService : JobService() {

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        Ln.d(TAG, "Performing long running task in scheduled job")
        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }

    companion object {

        private val TAG = "MyJobService"
    }

}