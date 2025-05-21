package ru.itis.second_sem.presentation.screens.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.itis.second_sem.R
import ru.itis.second_sem.domain.model.GraphModel

@Composable
fun GraphRoute(
    viewModel: GraphViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val onEvent = viewModel::onEvent

    GraphContent(state = state, onEvent = onEvent)
}


@Composable
fun GraphContent(state: GraphUIState, onEvent: (GraphEvent) -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = state.numberOfPoints,
                onValueChange = {
                    onEvent(GraphEvent.UpdateNumberOfPoints(it))
                },
                modifier = Modifier.padding(top = 50.dp),
                label = { Text(text = stringResource(id = R.string.enter_points)) }
            )
            TextField(
                value = state.values,
                onValueChange = { onEvent(GraphEvent.UpdateValues(it)) },
                modifier = Modifier.padding(top = 50.dp),
                label = { Text(text = stringResource(id = R.string.enter_values)) }
            )
            OutlinedButton(
                onClick = { onEvent(GraphEvent.DrawGraph) },
                modifier = Modifier.padding(top = 50.dp)
            ) {
                Text(text = stringResource(id = R.string.draw_graph))
            }

            state.errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (state.isDraw && state.errorMessage == null) {
                DrawGraph(
                    numberOfPoints = state.numberOfPoints.toInt(),
                    values = state.values.split(",").map { it.trim().toFloat() })
            }
        }
    }
}

@Preview
@Composable
fun DrawGraph(numberOfPoints: Int, values: List<Float>) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp)
    ) {
        val width = size.width
        val height = size.height
        val stepX = width / numberOfPoints
        val maxY = values.maxOrNull() ?: 1f
        val stepY = height / maxY

        drawLine(
            color = Color.Gray,
            start = Offset(0f, height),
            end = Offset(width, height),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.Gray,
            start = Offset(0f, 0f),
            end = Offset(0f, height),
            strokeWidth = 2f
        )

        val path = Path().apply {
            moveTo(0f, height - (values[0] * stepY))
        }
        values.forEachIndexed { index, value ->
            val x = stepX * index
            val y = height - (value * stepY)
            drawCircle(
                color = Color.Gray,
                radius = 5f,
                center = Offset(x, y)
            )
            if (index > 0) {
                path.lineTo(x, y)
            }
        }
        drawPath(
            path = path,
            color = Color(0xFF6A1B9A),
            style = Stroke(width = 2f)
        )

        val gradientPath = Path().apply {
            addPath(path)
            lineTo(stepX * (values.size - 1), height)
            lineTo(0f, height)
            close()
        }
        drawPath(
            path = gradientPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFCE93D8).copy(alpha = 0.5f),
                    Color.Transparent
                ),
                startY = height,
                endY = 0f
            )
        )
    }
}