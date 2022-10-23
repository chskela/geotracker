package com.chskela.geotracker.tracker.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel : ViewModel() {
    private val _performLocationAction: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val performLocationAction = _performLocationAction.asStateFlow()

    fun setPerformLocationAction(request: Boolean) {
        _performLocationAction.value = request
    }
}