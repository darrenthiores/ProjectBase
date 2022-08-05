package com.dev.base.viewModel

import androidx.lifecycle.ViewModel
import com.dev.core.domain.UseCase

class LandingViewModel(
    private val useCase: UseCase
): ViewModel() {
}