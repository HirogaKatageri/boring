package dev.hirogakatageri.boring.user.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class representing the response from the dummyjson.com/users API.
 * This includes pagination information and the list of users.
 * 
 * Based on the structure from dummyjson.com/users API:
 * {
 *   "users": [
 *     {
 *       "id": 1,
 *       "firstName": "John",
 *       ...
 *     },
 *     ...
 *   ],
 *   "total": 100,
 *   "skip": 0,
 *   "limit": 10
 * }
 */
@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "users")
    val users: List<User>,

    @Json(name = "total")
    val total: Int,

    @Json(name = "skip")
    val skip: Int,

    @Json(name = "limit")
    val limit: Int
) {
    // Helper method to check if there are more users to load
    fun hasMoreUsers(): Boolean {
        return skip + limit < total
    }
    
    // Helper method to get the next page number
    fun getNextPage(): Int {
        return skip / limit + 1
    }
    
    // Helper method to get the total number of pages
    fun getTotalPages(): Int {
        return (total + limit - 1) / limit
    }
}