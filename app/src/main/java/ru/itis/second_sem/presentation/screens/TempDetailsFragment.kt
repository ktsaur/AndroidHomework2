package ru.itis.second_sem.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.second_sem.R
import ru.itis.second_sem.databinding.FragmentTempdetailsBinding
import ru.itis.second_sem.presentation.ui.ErrorAlertDialog
import ru.itis.second_sem.presentation.ui.TempDetailsFragment

@AndroidEntryPoint
class TempDetailsFragment : BaseFragment(R.layout.fragment_tempdetails) {

    private val viewBinding: FragmentTempdetailsBinding by viewBinding(FragmentTempdetailsBinding::bind)

    private val viewModel: TempDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val city = arguments?.getString("ARG_CITY") ?: ""
        super.onViewCreated(view, savedInstanceState)
        viewModel.getForecast(city = city)
        viewModel.getCurrentTemp(city = city)

        initViews(city = city)
    }

    fun initViews(city: String) {
        viewBinding.composeContainerId.setContent {
            val uiState by viewModel.uiState.collectAsState()

            when{
                uiState?.error != null -> {
                    ErrorAlertDialog(
                        ex = uiState?.error ?: "Неизвестная ошибка" ,
                        onConfirmBack = { parentFragmentManager.popBackStack() } )
                }
                uiState?.weather != null && uiState?.forecast?.isNotEmpty() == true -> {
                    TempDetailsFragment(
                        city = city,
                        weatherUIState = uiState!!
                    )
                }
            }
        }
    }

/*    private fun observeData() {
        lifecycleScope.launch {
            viewModel.forecastFlow.observe(lifecycleOwner = this@TempDetailsFragment.viewLifecycleOwner) { data ->
                data?.forEach {
                    Log.i(
                        "TEST-TAG", "temp = ${it.temp}, " +
                                "tempMax = ${it.tempMax}, " +
                                "tempMin = ${it.tempMin}, " +
                                "dt = ${it.dt}, " +
                                "mainDesc = ${it.mainDesc}, " +
                                "description = ${it.description}, " +
                                "feelsLike = ${it.feelsLike}"
                    )
                }
            }
        }
    }*/

    companion object {
        private const val ARG_CITY = "ARG_CITY"

        fun bundle(text: String): Bundle = Bundle().apply {
            putString(ARG_CITY, text)
        }
    }
}