package me.amitshekhar.mvvm.ui.topheadline

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
import me.amitshekhar.mvvm.data.model.Game
import me.amitshekhar.mvvm.databinding.ActivityTopHeadlineBinding
import me.amitshekhar.mvvm.di.component.DaggerActivityComponent
import me.amitshekhar.mvvm.di.module.ActivityModule
import me.amitshekhar.mvvm.ui.base.UiState
import me.namnamnam.mvvm.ui.topheadline.LoginActivity
import me.namnamnam.mvvm.ui.topheadline.viewmodel.GamesViewModel
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {

//    @Inject
//    lateinit var topHeadlineViewModel: TopHeadlineViewModel

    @Inject
    lateinit var gamesViewModel: GamesViewModel

//    @Inject
//    lateinit var adapter: TopHeadlineAdapter

    @Inject
    lateinit var adapterGame: GameAdapter

    private lateinit var binding: ActivityTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI2()
        setupObserver2()
        binding.btnAccount.setOnClickListener {
            nextAct(LoginActivity::class.java)
        }
    }

//    private fun setupUI() {
//        val recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                recyclerView.context,
//                (recyclerView.layoutManager as LinearLayoutManager).orientation
//            )
//        )
//        recyclerView.adapter = adapter
//    }

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

//    private fun setupObserver() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                topHeadlineViewModel.uiState.collect {
//                    when (it) {
//                        is UiState.Success -> {
//                            binding.progressBar.visibility = View.GONE
//                            renderList(it.data)
//                            binding.recyclerView.visibility = View.VISIBLE
//                        }
//                        is UiState.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
//                            binding.recyclerView.visibility = View.GONE
//                        }
//                        is UiState.Error -> {
//                            //Handle Error
//                            binding.progressBar.visibility = View.GONE
//                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_LONG)
//                                .show()
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun setupObserver2() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                gamesViewModel.uiState.collect{
                    when(it){
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderGameList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
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

//    private fun renderList(articleList: List<Article>) {
//        adapter.addData(articleList)
//        adapter.notifyDataSetChanged()
//    }

    private fun renderGameList(gameList: List<Game>) {
        Log.e("dakar911", gameList.toString() )
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
