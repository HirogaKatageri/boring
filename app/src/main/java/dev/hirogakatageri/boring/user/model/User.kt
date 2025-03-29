package dev.hirogakatageri.boring.user.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class representing a user from the dummyjson.com API.
 * This is a Moshi JSON model for network responses and a Room entity for local caching.
 * 
 * Based on the structure from dummyjson.com/users API:
 * {
 *   "id": 1,
 *   "firstName": "John",
 *   "lastName": "Doe",
 *   "maidenName": "Smith",
 *   "age": 30,
 *   "gender": "male",
 *   "email": "john.doe@example.com",
 *   "phone": "+1234567890",
 *   "username": "johndoe",
 *   "password": "password123",
 *   "birthDate": "1993-07-20",
 *   "image": "https://example.com/image.jpg",
 *   "bloodGroup": "A+",
 *   "height": 180,
 *   "weight": 75,
 *   "eyeColor": "blue",
 *   "hair": {
 *     "color": "brown",
 *     "type": "straight"
 *   },
 *   "domain": "example.com",
 *   "ip": "192.168.1.1",
 *   "address": {
 *     "address": "123 Main St",
 *     "city": "New York",
 *     "coordinates": {
 *       "lat": 40.7128,
 *       "lng": -74.0060
 *     },
 *     "postalCode": "10001",
 *     "state": "NY"
 *   },
 *   "macAddress": "00:00:00:00:00:00",
 *   "university": "Harvard",
 *   "bank": {
 *     "cardExpire": "10/25",
 *     "cardNumber": "1234567890123456",
 *     "cardType": "visa",
 *     "currency": "USD",
 *     "iban": "US1234567890"
 *   },
 *   "company": {
 *     "address": {
 *       "address": "123 Business St",
 *       "city": "New York",
 *       "coordinates": {
 *         "lat": 40.7128,
 *         "lng": -74.0060
 *       },
 *       "postalCode": "10001",
 *       "state": "NY"
 *     },
 *     "department": "Engineering",
 *     "name": "Tech Corp",
 *     "title": "Software Engineer"
 *   },
 *   "ein": "12-3456789",
 *   "ssn": "123-45-6789",
 *   "userAgent": "Mozilla/5.0"
 * }
 */
@Entity(tableName = "users")
@JsonClass(generateAdapter = true)
data class User(
    @PrimaryKey
    @Json(name = "id")
    val id: Int,

    @Json(name = "firstName")
    val firstName: String,

    @Json(name = "lastName")
    val lastName: String,

    @Json(name = "maidenName")
    val maidenName: String?,

    @Json(name = "age")
    val age: Int,

    @Json(name = "gender")
    val gender: String,

    @Json(name = "email")
    val email: String,

    @Json(name = "phone")
    val phone: String,

    @Json(name = "username")
    val username: String,

    @Json(name = "image")
    val image: String?,

    @Json(name = "birthDate")
    val birthDate: String?,

    @Json(name = "height")
    val height: Double?,

    @Json(name = "weight")
    val weight: Double?,

    @Json(name = "university")
    val university: String?
) {
    // Helper method to get full name
    fun getFullName(): String {
        return "$firstName $lastName"
    }

    // Helper method to get display image or default if null
    fun getDisplayImage(): String {
        return image ?: "https://www.gravatar.com/avatar/?d=mp"
    }
}
