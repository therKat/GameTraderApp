package me.namnamnam.mvvm.ui.topheadline.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.amitshekhar.mvvm.ui.base.UiState
import me.namnamnam.mvvm.data.model.User
import me.namnamnam.mvvm.data.repository.GetUserRepository

class UsersViewModel(private val getUserRepository: GetUserRepository):ViewModel() {
    private val _usersList = MutableLiveData<List<User>>()
    val usersList: LiveData<List<User>> get() = _usersList

    private val _loggedInUserId = MutableLiveData<Int>()
    val loggedInUserId: LiveData<Int> get() = _loggedInUserId


    private val _uiState = MutableStateFlow<UiState<List<User>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<User>>> = _uiState

    fun setUsersList(users: List<User>) {
        _usersList.value = users
    }

    data class LoginResult(val userId: Int?, val userName: String?)

    fun login(email: String, password: String): LoginResult {
        val userList = _usersList.value ?: emptyList()

        for (user in userList) {
            if (user.email == email && user.password == password) {
                _loggedInUserId.value = user.id
                return LoginResult(user.id, user.name)
            }
        }

        return LoginResult(null, null)
    }
    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            getUserRepository.getUsers()
                .catch {e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}