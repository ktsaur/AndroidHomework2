package ru.itis.second_sem.presentation.screens.graph

import androidx.compose.runtime.Immutable
import ru.itis.second_sem.domain.model.GraphModel

@Immutable
data class GraphUIState(
    val numberOfPoints: String = "",
    val values: String = "",
    val isDraw: Boolean = false,
    val errorMessage: String? = null
)

sealed class GraphEvent {
    data object DrawGraph: GraphEvent()
    data class UpdateValues(val values: String): GraphEvent()
    data class UpdateNumberOfPoints(val number: String): GraphEvent()
}