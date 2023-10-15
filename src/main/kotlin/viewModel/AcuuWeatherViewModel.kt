package viewModel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import model.AccuWeatherRepostiory
import model.AdministrativeArea
import model.City
import model.Location

class AcuuWeatherViewModel {
    private val repository: AccuWeatherRepostiory = AccuWeatherRepostiory()

    private val _locations = MutableStateFlow(emptyList<Location>())
    val locations = _locations.asStateFlow().onEach { println(it) }

    private val scope = CoroutineScope(SupervisorJob())

    val selectedCountry = MutableStateFlow("")

    private val _countriesInSelectedRegion = MutableStateFlow(emptyList<Location>())
    val countriesInSelectedRegion = _countriesInSelectedRegion.asStateFlow()

    private val _administrativeAreasInCountry = MutableStateFlow(emptyList<AdministrativeArea>())
    val administrativeAreasInCountry = _administrativeAreasInCountry.asStateFlow()

    private val _queryResults = MutableStateFlow(emptyList<City>())
    val queryResults = _queryResults.asStateFlow()

    init {
        scope.launch {
            getLocationsList()
            val firstCountry = locations.first()[0]
            getCountriesInRegion(firstCountry.englishName, firstCountry.id)

        }
    }

    suspend fun getLocationsList() {
        _locations.emit(repository.fetchTemperatureForDay())
    }

    suspend fun getCountriesInRegion(countryName: String, regionKey: String) {
        scope.launch {
            selectedCountry.emit(countryName)
            getAdministrativeAresInRegion(regionKey)
            _countriesInSelectedRegion.emit(repository.getCountriesInRegion(regionKey))
        }
    }

    private suspend fun getAdministrativeAresInRegion(countryName: String) {
        scope.launch {
            _administrativeAreasInCountry.emit(repository.getAdministrativeAreasInCountry(countryName))
        }
    }

    suspend fun onQueryChanged(query: String) {
        _queryResults.emit(repository.searchCitiesByQuery(query))
    }
}