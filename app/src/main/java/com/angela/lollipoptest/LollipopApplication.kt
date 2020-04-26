package com.angela.lollipoptest

import android.app.Application
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.util.ServiceLocator
import kotlin.properties.Delegates

class LollipopApplication : MultiDexApplication() {

    val lollipopRepository: LollipopRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var INSTANCE: LollipopApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}