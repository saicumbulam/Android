package com.kickstarter.services

import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.kickstarter.KSApplication
import com.kickstarter.libs.Build
import com.kickstarter.ui.IntentKey
import io.fabric.sdk.android.Fabric
import okhttp3.ResponseBody
import retrofit2.Response
import rx.schedulers.Schedulers
import javax.inject.Inject

class KoalaBackgroundService : JobService() {
    @Inject
    lateinit var koala: KoalaService
    @Inject
    lateinit var build: Build


    override fun onCreate() {
        super.onCreate()
        (applicationContext as KSApplication).component().inject(this)
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        val extras = job?.extras
        val eventName = extras?.get(IntentKey.KOALA_EVENT_NAME) as String
        koala.track(extras.get(IntentKey.KOALA_EVENT) as String)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    logResponse(it, eventName)
                    jobFinished(job, !it.isSuccessful)

                }, {
                    logTrackingError(eventName)
                    jobFinished(job, false)
                })
        return true
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return true
    }

    private fun logResponse(it: Response<ResponseBody>, eventName: String) {
        if (it.isSuccessful) {
            if (this.build.isDebug) {
                Log.d(TAG, "Successfully tracked event: $eventName")
            }
        } else {
            logTrackingError(eventName)
        }
    }

    private fun logTrackingError(eventName: String) {
        if (this.build.isDebug) {
            Log.e(TAG, "Failed to track event: $eventName")
        }
        Fabric.getLogger().e(TAG, "Failed to track event: $eventName")
    }

    companion object {
        const val BASE_JOB_NAME = "Koala-Background-Service"
        val TAG = KoalaBackgroundService::class.java.simpleName +" \uD83D\uDC28"
    }
}
