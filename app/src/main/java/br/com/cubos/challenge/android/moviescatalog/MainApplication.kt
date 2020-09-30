package br.com.cubos.challenge.android.moviescatalog

import android.app.Application
import br.com.cubos.challenge.android.moviescatalog.di.koin.modules.applicationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            applicationModules
        }
    }

}
