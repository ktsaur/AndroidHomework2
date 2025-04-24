package ru.itis.second_sem.presentation.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.CreationExtras
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.second_sem.R
import ru.itis.second_sem.databinding.FragmentCurrentTempBinding
import ru.itis.second_sem.di.ServiceLocator
import ru.itis.second_sem.presentation.ui.CurrentTempFragmentCompose
import ru.itis.second_sem.presentation.utils.LegacyViewModelFactory
import ru.itis.second_sem.presentation.utils.MultibindingFactorySample
import ru.itis.second_sem.utils.appComponent
import javax.inject.Inject


class CurrentTempFragment : BaseFragment(R.layout.fragment_current_temp) {

    private val viewBinding: FragmentCurrentTempBinding by viewBinding(FragmentCurrentTempBinding::bind)
    private var inputValue by mutableStateOf("")

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var viewModel: CurrentTempViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = factory.create(CurrentTempViewModel::class.java, CreationExtras.Empty)
        //из фэктори создаем нужный нам экземпляр модели

        setViews()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().appComponent().inject(fragment = this)
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