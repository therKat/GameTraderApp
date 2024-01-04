package me.namnamnam.mvvm.data.repository

import me.amitshekhar.mvvm.data.api.NetworkService
import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.namnamnam.mvvm.data.model.User
import javax.inject.Singleton

@Singleton
class GetUserRepository @Inject constructor(private val networkService: NetworkService) {

    fun getUsers(): Flow<List<User>> {
        return flow {
            val userResponse = networkService.getAllUsers()
            emit(userResponse)
        }
    }


}
