package br.com.cubos.challenge.android.moviescatalog

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication: Application() {
    private val applicationModules = module {}

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(applicationModules)
        }
    }

}
