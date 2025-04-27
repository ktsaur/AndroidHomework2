package ru.itis.second_sem.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.itis.second_sem.R
import ru.itis.second_sem.databinding.FragmentCurrentTempBinding
import ru.itis.second_sem.presentation.ui.CurrentTempFragmentCompose
import ru.itis.second_sem.presentation.utils.observe

@AndroidEntryPoint
class CurrentTempFragment : BaseFragment(R.layout.fragment_current_temp) {

    private val viewBinding: FragmentCurrentTempBinding by viewBinding(FragmentCurrentTempBinding::bind)
    private var city by mutableStateOf("")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    fun setViews() {
        viewBinding.composeContainerId.setContent {
            CurrentTempFragmentCompose(
                onClick = { navigate() },
                value = city,
                onValueChange = { newValue ->
                    city = newValue
                }
            )
        }
    }

    fun navigate() {
        val tempDetailFragment = TempDetailsFragment().apply {
            arguments = TempDetailsFragment.bundle(text = city)
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, tempDetailFragment)
            .addToBackStack(null).commit()
    }

}