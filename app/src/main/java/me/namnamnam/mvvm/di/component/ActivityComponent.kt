package me.amitshekhar.mvvm.di.component

import dagger.Component
import me.amitshekhar.mvvm.di.ActivityScope
import me.amitshekhar.mvvm.di.module.ActivityModule
import me.amitshekhar.mvvm.ui.topheadline.TopHeadlineActivity
import me.namnamnam.mvvm.ui.splash.SplashActivity
import me.namnamnam.mvvm.ui.topheadline.LoginActivity

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: LoginActivity)

    fun inject(activity: SplashActivity)

}