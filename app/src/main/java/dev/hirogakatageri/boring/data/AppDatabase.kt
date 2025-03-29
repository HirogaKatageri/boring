package dev.hirogakatageri.boring.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.hirogakatageri.boring.user.local.UserDao
import dev.hirogakatageri.boring.user.model.User

/**
 * Room database for the application.
 * This is the single database instance for the entire application.
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Get the UserDao for accessing the users table.
     */
    abstract fun userDao(): UserDao

}
