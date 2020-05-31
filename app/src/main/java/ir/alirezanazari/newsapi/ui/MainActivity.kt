package ir.alirezanazari.newsapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.alirezanazari.newsapi.R
import ir.alirezanazari.newsapi.internal.Navigator
import ir.alirezanazari.newsapi.ui.sources.SourcesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Navigator.openSources(supportFragmentManager)
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1){
            val fragment = supportFragmentManager.findFragmentById(R.id.fragments_container)
            if(fragment is SourcesFragment){
                if(fragment.onBackPressed()) finish()
                return
            }else {
                finish()
                return
            }
        }
        super.onBackPressed()
    }
}
