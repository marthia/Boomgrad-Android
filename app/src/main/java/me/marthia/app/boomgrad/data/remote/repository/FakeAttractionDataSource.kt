package me.marthia.app.boomgrad.data.remote.repository


import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import me.marthia.app.boomgrad.R

@Serializable
data class MockAttraction(
    val id: Int,
    val title: String,
    val title_en: String,
    val description: String,
    val address: String,
    val phone: String,
    val hours: String,
    val entrance_fee: String,
    val image: String,
    val coordinates: Coordinates
)

@Serializable
data class Coordinates(
    val lat: Double,
    val lng: Double
)

@Serializable
data class MockAttractionResponse(
    val locations: List<MockAttraction>
)

object FakeAttractionDataSource {

    private var cachedLocations: List<MockAttraction>? = null

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun getAll(context: Context): List<MockAttraction> {
        // Return cached data if available
        cachedLocations?.let { return it }

        return try {
            // Read JSON from raw folder (assumes file is named locations.json in res/raw/)
            val inputStream = context.resources.openRawResource(R.raw.isfhan_top_locations)
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            // Parse JSON using Kotlin Serialization
            val response = json.decodeFromString<MockAttractionResponse>(jsonString)

            // Cache the result
            cachedLocations = response.locations

            response.locations
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Alternative method if you want to return a specific location by ID
    fun getById(context: Context, id: Int): MockAttraction? {
        return getAll(context).find { it.id == id }
    }

    // Clear cache if needed (useful for testing)
    fun clearCache() {
        cachedLocations = null
    }
}