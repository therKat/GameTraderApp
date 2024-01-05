package me.namnamnam.mvvm.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.amitshekhar.mvvm.data.api.NetworkService
import me.amitshekhar.mvvm.data.model.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGameByIdRepository @Inject constructor(private val networkService: NetworkService) {
    fun getGamesById(userId: Int): Flow<List<Game>> {
        return flow {
            val gamesResponse = networkService.getGamesByUserId(userId)
            emit(gamesResponse)
        }
    }
}