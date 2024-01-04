package me.namnamnam.mvvm.ui.topheadline.viewmodel

import androidx.lifecycle.ViewModel
import me.amitshekhar.mvvm.data.repository.TopHeadlineRepository

class TopHeadlineViewModel(private val topHeadlineRepository: TopHeadlineRepository) : ViewModel() {

//    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
//
//    val uiState: StateFlow<UiState<List<Article>>> = _uiState
//
//    init {
//        fetchTopHeadlines()
//    }
//
//    private fun fetchTopHeadlines() {
//        viewModelScope.launch {
//            topHeadlineRepository.getTopHeadlines(COUNTRY)
//                .catch { e ->
//                    _uiState.value = UiState.Error(e.toString())
//                }
//                .collect {
//                    _uiState.value = UiState.Success(it)
//                }
//        }
//    }

}