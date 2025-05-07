package ru.itis.second_sem.presentation.screens.currentTemp

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.second_sem.R
import ru.itis.second_sem.databinding.FragmentCurrentTempBinding
import ru.itis.second_sem.presentation.base.BaseFragment

@AndroidEntryPoint
class CurrentTempFragment : BaseFragment(R.layout.fragment_current_temp) {

    private val viewBinding: FragmentCurrentTempBinding by viewBinding(FragmentCurrentTempBinding::bind)
    private var city by mutableStateOf("")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

/*    fun navigate() {
        val tempDetailFragment = TempDetailsFragment().apply {
            arguments = TempDetailsFragment.bundle(text = city)
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, tempDetailFragment)
            .addToBackStack(null).commit()
    }*/

}