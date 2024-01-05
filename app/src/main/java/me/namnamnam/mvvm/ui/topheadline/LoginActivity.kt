package me.namnamnam.mvvm.ui.topheadline

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import me.amitshekhar.mvvm.MVVMApplication
import me.amitshekhar.mvvm.R
import me.amitshekhar.mvvm.databinding.ActivityLoginBinding
import me.amitshekhar.mvvm.di.component.DaggerActivityComponent
import me.amitshekhar.mvvm.di.module.ActivityModule
import me.amitshekhar.mvvm.ui.base.UiState
import me.amitshekhar.mvvm.ui.topheadline.TopHeadlineActivity
import me.amitshekhar.mvvm.utils.AppConstant.FIRST_TIME_KEY
import me.amitshekhar.mvvm.utils.AppConstant.LOGGED_IN_USER_ID
import me.amitshekhar.mvvm.utils.AppConstant.PREFS_NAME
import me.amitshekhar.mvvm.utils.AppConstant.USER_ID
import me.namnamnam.mvvm.ui.topheadline.viewmodel.UsersViewModel
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var usersViewModel: UsersViewModel

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
        setupUi()

        binding.btnLogin.setOnClickListener {

            val enteredEmail = binding.editTextEmail.text.toString()
            val enteredPassword = binding.editTextPassword.text.toString()

            val loginResult = usersViewModel.login(enteredEmail, enteredPassword)

            if (loginResult.userId != null) {
                saveLoggedInUserId(loginResult.userId)
                val userName = loginResult.userName
                firstTime(true)
                Log.e("dakar911 123", getFirstTime().toString() )
                showToast("Xin chào $userName")
                nextAct(TopHeadlineActivity::class.java)
            } else {
                showToast("Thông tin đăng nhập không chính xác!")
            }
        }
        binding.btnLogout.setOnClickListener {
            binding.accountLayout.visibility = View.INVISIBLE
            binding.loginLayout.visibility = View.VISIBLE

            firstTime(false)
        }
        binding.btnBack.setOnClickListener {
            nextAct(TopHeadlineActivity::class.java)
        }
    }

    private fun setupUi() {
        if(getFirstTime()){
            binding.accountLayout.visibility = View.VISIBLE
            binding.loginLayout.visibility = View.INVISIBLE
        }else{
            binding.accountLayout.visibility = View.INVISIBLE
            binding.loginLayout.visibility = View.VISIBLE
        }

        usersViewModel.usersList.observe(this) { users ->
            if (users.isNotEmpty()) {
                val firstUser = users[0]
                binding.tvName.text = firstUser.name
                binding.tvEmail.text = firstUser.email
            }
        }
    }

    private fun setupObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                usersViewModel.uiState.collect{
                    when(it){
                        is UiState.Success -> {
                            Log.e("dakar911", it.data.toString() )
                            usersViewModel.setUsersList(it.data)
                        }
                        is UiState.Loading -> {
                        }
                        is UiState.Error -> {
                            Log.e("dakar911", it.message)
                        }
                    }
                }
            }
        }
    }

    private fun saveLoggedInUserId(loggedInUserId: Int) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(USER_ID, loggedInUserId)
        editor.apply()
    }

    private fun firstTime(isFirstTime: Boolean){
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(FIRST_TIME_KEY,isFirstTime)
        editor.apply()
    }

    private fun getFirstTime(): Boolean{
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(FIRST_TIME_KEY, false)
    }


    private fun getLoggedInUserId(): Int {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt(USER_ID, -1)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MVVMApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

    private fun nextAct(activity: Class<*>, bundle: Bundle? = null){
        this.startActivity(Intent(this, activity).apply {
            bundle?.let {
                putExtras(bundle)
            }
        })
    }

}