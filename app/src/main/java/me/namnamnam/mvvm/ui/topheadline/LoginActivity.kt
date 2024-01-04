package me.namnamnam.mvvm.ui.topheadline

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
import me.namnamnam.mvvm.data.preferences.SharedPreferences
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

        binding.btnLogin.setOnClickListener {
            val enteredEmail = binding.editTextEmail.text.toString()
            val enteredPassword = binding.editTextPassword.text.toString()

            val loggedInUserId = usersViewModel.login(enteredEmail, enteredPassword)

            if (loggedInUserId != null) {

                Log.d("namnamnam", "Đăng nhập thành công! ID: $loggedInUserId")
            } else {
                Log.e("namnamnam", "Thông tin đăng nhập không chính xác!")
            }
        }
    }

    private fun setupObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                usersViewModel.uiState.collect{
                    when(it){
                        is UiState.Success -> {
                            Log.e("nam123", it.data.toString() )
                            usersViewModel.setUsersList(it.data)
                        }
                        is UiState.Loading -> {
                        }
                        is UiState.Error -> {
                            Log.e("namnamnam 123", it.message)
                        }
                    }
                }
            }
        }
    }

    private fun saveLoggedInUserId(loggedInUserId: Int) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("user_id", loggedInUserId)
        editor.apply()
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