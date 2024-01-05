package me.namnamnam.mvvm.ui.topheadline.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.amitshekhar.mvvm.data.model.Game
import me.amitshekhar.mvvm.ui.base.UiState
import me.namnamnam.mvvm.data.repository.GetGameByIdRepository

class GamesByIdViewModel(private val getGameByIdRepository: GetGameByIdRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Game>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Game>>> = _uiState

    private var userId: Int = 1 // Default value, you can set it to an appropriate default

    init {
        // You may want to set a default userId or leave it as is, depending on your requirements
        // fetchGamesById(1)
    }

    fun setUserId(userId: Int) {
        this.userId = userId
        fetchGamesById()
    }

    private fun fetchGamesById() {
        viewModelScope.launch {
            getGameByIdRepository.getGamesById(userId)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}
