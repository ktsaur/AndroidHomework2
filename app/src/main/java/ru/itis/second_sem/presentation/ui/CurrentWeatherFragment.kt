package ru.itis.second_sem.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.itis.second_sem.R
import ru.itis.second_sem.presentation.screens.CurrentTempFragment

@Composable
fun CurrentTempFragmentCompose(
    value: String,
    onClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    Scaffold { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
        ) {
            var text by remember { mutableStateOf(value) } //локальное состояние внутри composable
            TextField(
                value = text,
                onValueChange = { text = it
                                onValueChange(it)},
                label = { Text(text = stringResource(id = R.string.enter_city)) }
            )
            OutlinedButton(
                onClick = { onClick() },
                modifier = Modifier.padding(top = 50.dp)
            ) {
                Text(text = stringResource(id = R.string.request))
            }
        }
    }
}

@Preview
@Composable
fun CurrentTempFragmentPreview() {
    CurrentTempFragmentCompose(value = "", onClick = {}, onValueChange = {})
}
