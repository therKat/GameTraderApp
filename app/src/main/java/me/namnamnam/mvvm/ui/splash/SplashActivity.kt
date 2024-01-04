package me.namnamnam.mvvm.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import me.amitshekhar.mvvm.R
import me.amitshekhar.mvvm.databinding.ActivitySplashBinding
import me.amitshekhar.mvvm.ui.topheadline.TopHeadlineActivity
import me.namnamnam.mvvm.ui.topheadline.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            nextAct(LoginActivity::class.java)
        }, 1000)

    }

    private fun nextAct(activity: Class<*>, bundle: Bundle? = null){
        this.startActivity(Intent(this, activity).apply {
            bundle?.let {
                putExtras(bundle)
            }
        })
    }
}