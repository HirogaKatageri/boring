package dev.hirogakatageri.boring.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hirogakatageri.boring.user.model.User
import dev.hirogakatageri.boring.user.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * ViewModel for the user screen.
 */
class UserViewModel(private val repository: UserRepository) : ViewModel() {

    // UI state for the user list
    sealed class UserUiState {
        data object Loading : UserUiState()
        data class Success(val users: List<User>, val hasMoreData: Boolean) : UserUiState()
        data class Error(val message: String) : UserUiState()
    }

    // Expose UI state as StateFlow
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState

    // Selected user state
    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser

    // Pagination state
    private var currentPage = 0
    private val pageSize = 10
    private var isLoading = false
    private var hasMoreData = true

    init {
        loadUsers()
    }

    /**
     * Load users from the repository.
     */
    fun loadUsers() {
        if (isLoading || !hasMoreData) return

        isLoading = true
        viewModelScope.launch {
            val skip = currentPage * pageSize

            repository.getUsers(limit = pageSize, skip = skip)
                .catch { e ->
                    _uiState.value = UserUiState.Error(e.message ?: "Unknown error")
                    isLoading = false
                }
                .collect { response ->
                    val currentUsers = if (_uiState.value is UserUiState.Success) {
                        (_uiState.value as UserUiState.Success).users
                    } else {
                        emptyList()
                    }

                    val newUsers = currentUsers + response.users
                    hasMoreData = response.hasMoreUsers()
                    currentPage++

                    _uiState.value = if (newUsers.isEmpty()) {
                        UserUiState.Error("No users found")
                    } else {
                        UserUiState.Success(newUsers, hasMoreData)
                    }

                    isLoading = false
                }
        }
    }

    /**
     * Refresh the user list.
     */
    fun refreshUsers() {
        currentPage = 0
        hasMoreData = true
        _uiState.value = UserUiState.Loading
        loadUsers()
    }

    /**
     * Select a user by ID.
     * 
     * @param userId The ID of the user to select
     */
    fun selectUser(userId: Int) {
        viewModelScope.launch {
            _selectedUser.value = null // Reset while loading
            try {
                repository.getUserById(userId)
                    .catch { e ->
                        // Handle error, but keep selectedUser as null
                        println("Error loading user: ${e.message}")
                    }
                    .collect { user ->
                        _selectedUser.value = user
                    }
            } catch (e: Exception) {
                // Handle any other exceptions
                println("Exception loading user: ${e.message}")
            }
        }
    }

}
