package ru.itis.second_sem.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.second_sem.R
import ru.itis.second_sem.databinding.FragmentTempdetailsBinding

@AndroidEntryPoint
class TempDetailsFragment : BaseFragment(R.layout.fragment_tempdetails) {

    private val viewBinding: FragmentTempdetailsBinding by viewBinding(FragmentTempdetailsBinding::bind)

    private val viewModel: TempDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start()
    }
}