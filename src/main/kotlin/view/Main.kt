package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import viewModel.AcuuWeatherViewModel

@Composable
@Preview
fun App() {
    val viewModel = AcuuWeatherViewModel()
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

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
