package me.namnamnam.mvvm.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import me.amitshekhar.mvvm.R
import me.amitshekhar.mvvm.databinding.ActivitySplashBinding
import me.amitshekhar.mvvm.ui.topheadline.TopHeadlineActivity
import me.amitshekhar.mvvm.utils.AppConstant
import me.amitshekhar.mvvm.utils.AppConstant.FIRST_TIME_KEY
import me.namnamnam.mvvm.ui.topheadline.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (getFirstTime()) {
                nextAct(TopHeadlineActivity::class.java)
            }else{
                nextAct(LoginActivity::class.java)
            }

        }, 1000)

    }
    private fun getFirstTime(): Boolean{
        val sharedPreferences = getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(FIRST_TIME_KEY, false)
    }
    private fun nextAct(activity: Class<*>, bundle: Bundle? = null){
        this.startActivity(Intent(this, activity).apply {
            bundle?.let {
                putExtras(bundle)
            }
        })
    }
}