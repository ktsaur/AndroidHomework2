package ru.itis.second_sem.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import ru.itis.second_sem.R
import ru.itis.second_sem.databinding.FragmentTempdetailsBinding
import ru.itis.second_sem.presentation.ui.ErrorAlertDialog
import ru.itis.second_sem.presentation.ui.TempDetailsFragment
import ru.itis.second_sem.presentation.utils.CityValidationException
import ru.itis.second_sem.presentation.utils.Constants
import java.io.IOException

@AndroidEntryPoint
class TempDetailsFragment : BaseFragment(R.layout.fragment_tempdetails) {

    private val viewBinding: FragmentTempdetailsBinding by viewBinding(FragmentTempdetailsBinding::bind)

    private val viewModel: TempDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val city = arguments?.getString(Constants.ARG_CITY) ?: ""
        super.onViewCreated(view, savedInstanceState)
        cityValidationException(city = city)
        initViews(city = city)
    }

    fun initViews(city: String) {
        viewBinding.composeContainerId.setContent {
            val uiState by viewModel.uiState.collectAsState()
            val error = uiState.error

            if (error != null) {
                ErrorAlertDialog(
                    ex = getErrorMessage(ex = error, context = LocalContext.current),
                    onConfirmBack = { parentFragmentManager.popBackStack() } )
            } else if (uiState.forecast?.isNotEmpty() == true ) {
                TempDetailsFragment(
                    city = city,
                    weatherUIState = uiState
                )
            }
        }
    }

    private fun cityValidationException(city: String) {
        when {
            city.firstOrNull()?.isLowerCase() == true -> {
                viewModel.setError(CityValidationException())
            }
            else -> {
                viewModel.getForecast(city = city)
            }
        }
    }

    private fun getErrorMessage(ex: Throwable, context: Context): String {
        return when (ex) {
            is HttpException -> {
                when (ex.code()) {
                    401 -> context.getString(R.string.error_authentication)
                    404 -> context.getString(R.string.error_city_not_found)
                    else -> context.getString(R.string.error_server, ex.code())
                }
            }
            is IOException -> context.getString(R.string.error_connection)
            is CityValidationException -> context.getString(R.string.error_invalid_capitalization)
            else -> context.getString(R.string.error_unknown)
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
        fun bundle(text: String): Bundle = Bundle().apply {
            putString(Constants.ARG_CITY, text)
        }
    }
}