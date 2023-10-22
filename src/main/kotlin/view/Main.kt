package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.appModule
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import viewModel.AcuuWeatherViewModel

@Composable
fun App(viewModel: AcuuWeatherViewModel) {
    val coroutineScope = rememberCoroutineScope()

    val locations by viewModel.locations.collectAsState(initial = emptyList())
    val country by viewModel.selectedCountry.collectAsState("")
    val countriesInSelectedRegion by viewModel.countriesInSelectedRegion.collectAsState()
    val administrativeAresInCountry by viewModel.administrativeAreasInCountry.collectAsState()
    val queryResults by viewModel.queryResults.collectAsState()

    var query by mutableStateOf("")
    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
            TextButton(onClick = {
                coroutineScope.launch {
                    viewModel.getLocationsList()
                }
            }) {
                Text("Refresh")
            }
            Row {
                TextField(value = query, onValueChange = { query = it }, modifier = Modifier.weight(1f))
                TextButton(modifier = Modifier.wrapContentSize(),
                    onClick = { coroutineScope.launch { viewModel.onQueryChanged(query) } }) {
                    Text("Search city")
                }
            }

            Row(modifier = Modifier.fillMaxSize()) {

                ResourceColumn(Modifier.weight(1f), "Locations", locations) {
                    coroutineScope.launch {
                        viewModel.getCountriesInRegion(
                            it.englishName,
                            it.id
                        )
                    }
                }

                ResourceColumn(Modifier.weight(1f), "Countries in selected location", countriesInSelectedRegion) {}
                ResourceColumn(
                    Modifier.weight(1f),
                    "Administrative ares in selected country",
                    administrativeAresInCountry
                ) {}
                ResourceColumn(Modifier.weight(1f), "Found cities with query: ", queryResults) {}
            }
        }
    }
}

class AccuWeatherApplication : KoinComponent {
    val viewModel : AcuuWeatherViewModel by inject()

    fun fetchViewmodel() = viewModel
}

fun main() {
    startKoin {
        modules(appModule)
    }
    val app = AccuWeatherApplication()
    application {
        Window(onCloseRequest = ::exitApplication) {
            App(app.viewModel)
        }
    }
}

