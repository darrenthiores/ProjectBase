package com.dev.base.di

import com.dev.core.domain.Interactor
import com.dev.core.domain.UseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<UseCase> { Interactor(get()) }
}

val viewModelModule = module {
//    viewModel { }
}