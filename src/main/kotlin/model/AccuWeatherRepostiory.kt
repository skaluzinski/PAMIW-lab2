package model

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val API_ENDPOINT = "http://dataservice.accuweather.com/"
private const val API_KEY = "qQp54atvztCkjUnsovV9pejmRcDXE0UY"


class AccuWeatherRepostiory() {
    private val client =         HttpClient() {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Auth) {
            headers {
                headersOf("apikey", API_KEY)
            }
        }
    }


    suspend fun fetchTemperatureForDay(): List<Location> {
        val response: HttpResponse = httpResponse("locations/v1/regions")
        return response.body()
    }

    suspend fun getCountriesInRegion(countryKey: String): List<Location> {
        val response: HttpResponse = httpResponse("locations/v1/countries/$countryKey")
        return response.body()
    }

    suspend fun getAdministrativeAreasInCountry(countryKey: String): List<AdministrativeArea> {
        val response: HttpResponse = httpResponse("locations/v1/adminareas/$countryKey")
        return response.body()
    }

    private suspend fun httpResponse(path: String): HttpResponse {
        val response: HttpResponse = client.get(API_ENDPOINT) {
            url {
                protocol = URLProtocol.HTTP
                host = "dataservice.accuweather.com"
                path(path)
                parameter("apikey", API_KEY)
            }
        }
        return response
    }

    suspend fun searchCitiesByQuery(query: String): List<City> {
        val response:  HttpResponse = httpResponse("/locations/v1/cities/autocomplete&q=$query")
        return response.body()
    }
}
