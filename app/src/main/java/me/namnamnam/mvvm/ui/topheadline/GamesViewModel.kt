package me.amitshekhar.mvvm.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.amitshekhar.mvvm.data.model.Article
import me.amitshekhar.mvvm.data.model.Game
import me.amitshekhar.mvvm.data.repository.GetGameRepository
import me.amitshekhar.mvvm.ui.base.UiState

class GamesViewModel(private val getGameRepository: GetGameRepository):ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Game>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Game>>> = _uiState

    init {
        fetchGames()
    }

    private fun fetchGames() {
        viewModelScope.launch {
            getGameRepository.getGames()
                .catch {e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}