package ir.alirezanazari.newsapi

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import ir.alirezanazari.newsapi.internal.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class G: Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this);
        startKoin {
            androidContext(this@G)
            modules(appModules)
        }
    }
}