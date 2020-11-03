package com.app.pagedlibraryapplication

import android.app.Application
import com.app.pagedlibraryapplication.module.viewModelModule
import com.app.pagedlibraryapplication.utills.ReleaseTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        // start Koin!
        startKoin {
            // Android context
            androidContext(this@MyApplication)
            // modules
            modules(viewModelModule)
        }
    }
}