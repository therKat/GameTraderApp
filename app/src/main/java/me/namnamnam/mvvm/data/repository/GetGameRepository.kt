package me.amitshekhar.mvvm.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.amitshekhar.mvvm.data.api.NetworkService
import me.amitshekhar.mvvm.data.model.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGameRepository @Inject constructor(private val networkService: NetworkService) {

    fun getGames(): Flow<List<Game>> {
        return flow {
            val gamesResponse = networkService.getGames()
            emit(gamesResponse)
        }
    }

}