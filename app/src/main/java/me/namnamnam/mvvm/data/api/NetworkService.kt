package me.amitshekhar.mvvm.data.api

import me.amitshekhar.mvvm.data.model.Game
import me.namnamnam.mvvm.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET("/users")
    suspend fun getAllUsers(): List<User>

    @GET("/users/{user_id}")
    suspend fun getUserById(@Path("user_id") userId: Int): User

    @GET("/games")
    suspend fun getGames(): List<Game>

    @GET("transactions/user/{user_id}")
    suspend fun getGamesByUserId(@Path("user_id") userId: Int): List<Game>
//    @GET("/transactions/user/1")
//    suspend fun getGamesByUserId(): List<Game>

}