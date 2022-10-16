package com.vladds.foosballking.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class FoosballViewModelFactory @Inject constructor(
    private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards ViewModel>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModels[modelClass] as T
}
