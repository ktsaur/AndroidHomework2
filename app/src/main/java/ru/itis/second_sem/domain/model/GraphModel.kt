package ru.itis.second_sem.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class GraphModel (
    val numberOfPoints: Int = 0,
    val values: List<Float> = emptyList(),
)