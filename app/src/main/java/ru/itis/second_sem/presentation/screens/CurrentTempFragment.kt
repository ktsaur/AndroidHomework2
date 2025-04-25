package ru.itis.second_sem.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.second_sem.R
import ru.itis.second_sem.databinding.FragmentCurrentTempBinding
import ru.itis.second_sem.presentation.ui.CurrentTempFragmentCompose
import javax.inject.Inject

@AndroidEntryPoint
class CurrentTempFragment : BaseFragment(R.layout.fragment_current_temp) {

    private val viewBinding: FragmentCurrentTempBinding by viewBinding(FragmentCurrentTempBinding::bind)
    private var inputValue by mutableStateOf("")

    private val viewModel: CurrentTempViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    fun setViews() {
        viewBinding.composeContainerId.setContent {
            val currentWeather by viewModel.currentWeatherFlow.collectAsState() //collectAsState только для Compose.
            // он подписывается на flow внутри compose функции

            CurrentTempFragmentCompose(
                onClick = { viewModel.getCurrentWeather(inputValue) },
                value = inputValue,
                onValueChange = { newValue ->
                    inputValue = newValue
                },
                temperature = currentWeather?.currentTemp.toString())
        }
    }

}