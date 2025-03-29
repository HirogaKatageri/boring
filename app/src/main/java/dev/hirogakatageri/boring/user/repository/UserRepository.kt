package dev.hirogakatageri.boring.user.repository

import dev.hirogakatageri.boring.user.local.UserDao
import dev.hirogakatageri.boring.user.model.User
import dev.hirogakatageri.boring.user.model.UserResponse
import dev.hirogakatageri.boring.user.remote.UserApiService
import dev.hirogakatageri.boring.util.NetworkConnectivityUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

/**
 * Repository for handling user data operations.
 * This repository handles both remote and local data sources.
 * When online, it fetches data from the remote API and caches it in the local database.
 * When offline, it fetches data from the local database.
 */
class UserRepository(
    private val userApiService: UserApiService,
    private val userDao: UserDao
) {
    /**
     * Get users with pagination support.
     * 
     * @param limit The number of users to fetch per page (default: 10)
     * @param skip The number of users to skip (for pagination)
     * @return Flow of UserResponse containing the list of users and pagination information
     */
    fun getUsers(limit: Int = 10, skip: Int = 0): Flow<UserResponse> = flow {
        val isOnline = NetworkConnectivityUtil.isOnline()

        if (isOnline) {
            try {
                // Fetch from network
                val response = userApiService.getUsers(limit, skip)

                // Cache users in the database
                userDao.insertUsers(response.users)

                emit(response)
            } catch (e: IOException) {
                // Network error, fallback to local cache
                val totalCount = userDao.getUserCount()
                userDao.getUsersPaginated(limit, skip).collect { users ->
                    val response = UserResponse(
                        users = users,
                        total = totalCount,
                        skip = skip,
                        limit = limit
                    )
                    emit(response)
                }
            } catch (e: HttpException) {
                // HTTP error, fallback to local cache
                val totalCount = userDao.getUserCount()
                userDao.getUsersPaginated(limit, skip).collect { users ->
                    val response = UserResponse(
                        users = users,
                        total = totalCount,
                        skip = skip,
                        limit = limit
                    )
                    emit(response)
                }
            }
        } else {
            // Offline, use local cache
            val totalCount = userDao.getUserCount()
            userDao.getUsersPaginated(limit, skip).collect { users ->
                val response = UserResponse(
                    users = users,
                    total = totalCount,
                    skip = skip,
                    limit = limit
                )
                emit(response)
            }
        }
    }

    /**
     * Get a specific user by ID.
     * 
     * @param userId The ID of the user to fetch
     * @return Flow of User
     */
    fun getUserById(userId: Int): Flow<User> = flow {
        val isOnline = NetworkConnectivityUtil.isOnline()

        if (isOnline) {
            try {
                // Try to fetch from network first
                val response = userApiService.getUsers(limit = 100, skip = 0)
                val user = response.users.find { it.id == userId }

                if (user != null) {
                    // Cache the user in the database
                    userDao.insertUser(user)
                    emit(user)
                } else {
                    // User not found in network, try local cache
                    userDao.getUserById(userId).collect { cachedUser ->
                        if (cachedUser != null) {
                            emit(cachedUser)
                        } else {
                            throw NoSuchElementException("User with ID $userId not found")
                        }
                    }
                }
            } catch (e: Exception) {
                // Error fetching from network, try local cache
                userDao.getUserById(userId).collect { cachedUser ->
                    if (cachedUser != null) {
                        emit(cachedUser)
                    } else {
                        throw NoSuchElementException("User with ID $userId not found in cache")
                    }
                }
            }
        } else {
            // Offline, use local cache
            userDao.getUserById(userId).collect { cachedUser ->
                if (cachedUser != null) {
                    emit(cachedUser)
                } else {
                    throw NoSuchElementException("User with ID $userId not found in cache")
                }
            }
        }
    }

}
