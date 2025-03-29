package dev.hirogakatageri.boring.user.remote

import dev.hirogakatageri.boring.user.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API service interface for fetching users from dummyjson.com.
 */
interface UserApiService {
    /**
     * Get users from the API with pagination support.
     * 
     * @param limit The number of users to fetch per page (default: 10)
     * @param skip The number of users to skip (for pagination)
     * @return UserResponse containing the list of users and pagination information
     */
    @GET("users")
    suspend fun getUsers(
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int = 0
    ): UserResponse
}