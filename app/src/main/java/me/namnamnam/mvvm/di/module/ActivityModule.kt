package me.amitshekhar.mvvm.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.amitshekhar.mvvm.data.repository.GetGameRepository
import me.amitshekhar.mvvm.di.ActivityContext
import me.amitshekhar.mvvm.ui.base.ViewModelProviderFactory
import me.amitshekhar.mvvm.ui.topheadline.GameAdapter
import me.namnamnam.mvvm.data.repository.GetGameByIdRepository
import me.namnamnam.mvvm.data.repository.GetUserRepository
import me.namnamnam.mvvm.ui.topheadline.viewmodel.GamesByIdViewModel
import me.namnamnam.mvvm.ui.topheadline.viewmodel.GamesViewModel
import me.namnamnam.mvvm.ui.topheadline.viewmodel.UsersViewModel

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    //    @Provides
//    fun provideTopHeadlineViewModel(topHeadlineRepository: TopHeadlineRepository): TopHeadlineViewModel {
//        return ViewModelProvider(activity,
//            ViewModelProviderFactory(TopHeadlineViewModel::class) {
//                TopHeadlineViewModel(topHeadlineRepository)
//            })[TopHeadlineViewModel::class.java]
//    }
    @Provides
    fun provideUserViewModel(getUserRepository: GetUserRepository): UsersViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(UsersViewModel::class) {
                UsersViewModel(getUserRepository)
            })[UsersViewModel::class.java]
    }

    @Provides
    fun provideGameViewModel(getGameRepository: GetGameRepository): GamesViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(GamesViewModel::class) {
                GamesViewModel(getGameRepository)
            })[GamesViewModel::class.java]
    }

//    @Provides
//    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideGameByIdViewModel(getGameByIdRepository: GetGameByIdRepository): GamesByIdViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(GamesByIdViewModel::class){
                GamesByIdViewModel(getGameByIdRepository)
            })[GamesByIdViewModel::class.java]
    }
    @Provides
    fun provideGameAdapter() = GameAdapter(ArrayList())


}