package ru.itis.second_sem.presentation.screens.tempDetail

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.second_sem.R
import ru.itis.second_sem.databinding.FragmentTempdetailsBinding
import ru.itis.second_sem.presentation.base.BaseFragment
import ru.itis.second_sem.presentation.utils.Constants

@AndroidEntryPoint
class TempDetailsFragment : BaseFragment(R.layout.fragment_tempdetails) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}