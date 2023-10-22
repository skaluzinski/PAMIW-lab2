package di

import model.AccuWeatherRepostiory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import viewModel.AcuuWeatherViewModel


val appModule = module {
    singleOf(::AccuWeatherRepostiory)
    singleOf(::AcuuWeatherViewModel)
}