package me.namnamnam.mvvm.data.preferences

import android.content.Context
import android.content.SharedPreferences
import me.amitshekhar.mvvm.utils.AppConstant
import javax.inject.Inject

class SharedPreferences @Inject constructor(private val context: Context){
    private fun getPref(context : Context): SharedPreferences {
        return context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        )
    }
    var isFirstOpen: Boolean
        get() = getPref(context).getBoolean(AppConstant.FIRST_OPEN_APP, true)
        set(isFirstOpen) = getPref(context).edit().putBoolean(AppConstant.FIRST_OPEN_APP, isFirstOpen).apply()

    var loggedInUserId: Int
        get() = getPref(context).getInt(AppConstant.LOGGED_IN_USER_ID, -1)
        set(value) = getPref(context).edit().putInt(AppConstant.LOGGED_IN_USER_ID, value).apply()
}