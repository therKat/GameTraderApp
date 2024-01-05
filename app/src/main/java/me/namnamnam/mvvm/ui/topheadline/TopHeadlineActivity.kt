package me.amitshekhar.mvvm.ui.topheadline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import me.amitshekhar.mvvm.MVVMApplication
import me.amitshekhar.mvvm.R
import me.amitshekhar.mvvm.data.model.Game
import me.amitshekhar.mvvm.databinding.ActivityTopHeadlineBinding
import me.amitshekhar.mvvm.di.component.DaggerActivityComponent
import me.amitshekhar.mvvm.di.module.ActivityModule
import me.amitshekhar.mvvm.ui.base.UiState
import me.amitshekhar.mvvm.utils.AppConstant
import me.namnamnam.mvvm.ui.topheadline.LoginActivity
import me.namnamnam.mvvm.ui.topheadline.viewmodel.GamesByIdViewModel
import me.namnamnam.mvvm.ui.topheadline.viewmodel.GamesViewModel
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {
    @Inject
    lateinit var gamesViewModel: GamesViewModel

    @Inject
    lateinit var gamesByIdViewModel: GamesByIdViewModel

    @Inject
    lateinit var adapterGame: GameAdapter

    private lateinit var binding: ActivityTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gamesByIdViewModel.setUserId(getLoggedInUserId())
        setupUI2()
        setupObserver2()
        binding.btnAccount.setOnClickListener {
            nextAct(LoginActivity::class.java)
        }
        binding.btnLib.setOnClickListener {
            setupUI()
            setupObserver()
            binding.title.text = "Library"
            binding.btnBack.visibility = View.VISIBLE
            binding.iconApp.visibility = View.GONE
            binding.recyclerView2.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.footerLayout.visibility = View.GONE
        }
        binding.btnBack.setOnClickListener {
            setupUI2()
            setupObserver2()
            binding.btnBack.visibility = View.GONE
            binding.iconApp.visibility = View.VISIBLE
            binding.footerLayout.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.VISIBLE
            binding.recyclerView2.visibility = View.GONE
            binding.title.text = getString(R.string.app_name)
        }
    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView2
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapterGame
    }

    private fun setupUI2() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapterGame
    }

    private fun setupObserver2() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                gamesViewModel.uiState.collect{
                    when(it){
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            renderGameList(it.data)
                        }
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                            Log.e("dakar911", it.message )
                        }
                    }
                }
            }
        }
    }

    private fun setupObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                gamesByIdViewModel.uiState.collect{
                    when(it){
                        is UiState.Success -> {
                            Log.e("dakar911 1234", it.toString() )
                            renderGameList(it.data)
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView2.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView2.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                            Log.e("dakar911 2313", it.message )
                        }
                    }
                }
            }
        }

    }

//    private fun renderList(articleList: List<Article>) {
//        adapter.addData(articleList)
//        adapter.notifyDataSetChanged()
//    }

    private fun getLoggedInUserId(): Int {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt(AppConstant.USER_ID, -1)
    }

    private fun renderGameList(gameList: List<Game>) {
        adapterGame.clearData()
        Log.e("dakar911 eee", gameList.toString() )
        adapterGame.addData(gameList)
        adapterGame.notifyDataSetChanged()
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
