package com.angela.lollipoptest

import android.app.Application
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.util.ServiceLocator
import kotlin.properties.Delegates

class LollipopApplication : Application() {

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