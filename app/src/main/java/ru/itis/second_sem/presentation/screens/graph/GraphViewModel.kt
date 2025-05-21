package ru.itis.second_sem.presentation.screens.graph

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(GraphUIState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: GraphEvent) {
        when (event) {
            is GraphEvent.DrawGraph -> {
                val values = parseValues(_uiState.value.values)
                val numberOfPoints = _uiState.value.numberOfPoints.toIntOrNull()

                if (values == null || numberOfPoints == null || values.size != numberOfPoints) {
                    _uiState.update { it.copy(errorMessage = "Некорректно введены данные", isDraw = false) }
                }

                if (values != null && numberOfPoints != null) {
                    _uiState.update { it.copy(isDraw = true) }
                }
            }

            is GraphEvent.UpdateValues -> {
                _uiState.update { it.copy(values = event.values, isDraw = false, errorMessage = null) }
            }

            is GraphEvent.UpdateNumberOfPoints -> {
                _uiState.update { it.copy(numberOfPoints = event.number, isDraw = false, errorMessage = null) }
            }
        }
    }

    private fun parseValues(input: String): List<Float>? {
        if (input.isEmpty()) return null
        val parsedValues = input.split(",").mapNotNull { value ->
            val trimmedValue = value.trim()
            if (trimmedValue.isNotEmpty()) {
                val number = trimmedValue.toFloat()
                if (number >= 0) number else null
            } else null
        }
        return if (parsedValues.isNotEmpty()) parsedValues else null
    }
}