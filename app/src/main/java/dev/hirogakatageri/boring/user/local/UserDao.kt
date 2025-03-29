package dev.hirogakatageri.boring.user.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.hirogakatageri.boring.user.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for User entities.
 * This interface defines methods for accessing the users table in the database.
 */
@Dao
interface UserDao {
    /**
     * Insert a list of users into the database.
     * If a user with the same ID already exists, it will be replaced.
     * 
     * @param users The list of users to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)
    
    /**
     * Insert a single user into the database.
     * If a user with the same ID already exists, it will be replaced.
     * 
     * @param user The user to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    
    /**
     * Get all users from the database.
     * 
     * @return Flow of list of users
     */
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): Flow<List<User>>
    
    /**
     * Get a specific user by ID.
     * 
     * @param userId The ID of the user to get
     * @return Flow of user
     */
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    fun getUserById(userId: Int): Flow<User?>
    
    /**
     * Get a paginated list of users.
     * 
     * @param limit The number of users to get
     * @param offset The number of users to skip
     * @return Flow of list of users
     */
    @Query("SELECT * FROM users ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getUsersPaginated(limit: Int, offset: Int): Flow<List<User>>
    
    /**
     * Delete all users from the database.
     */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
    
    /**
     * Get the count of users in the database.
     * 
     * @return The number of users in the database
     */
    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
}