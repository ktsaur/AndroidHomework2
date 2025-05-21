package ru.itis.second_sem.presentation.screens.graph

import androidx.compose.runtime.Immutable

@Immutable
data class GraphUIState(
    val numberOfPoints: String = "",
    val values: String = "",
    val isDraw: Boolean = false,
    val errorMessage: Int? = null
)

sealed class GraphEvent {
    data object DrawGraph: GraphEvent()
    data class UpdateValues(val values: String): GraphEvent()
    data class UpdateNumberOfPoints(val number: String): GraphEvent()
}