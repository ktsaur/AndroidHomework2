package ru.itis.second_sem.presentation.screens

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.second_sem.R
import ru.itis.second_sem.databinding.FragmentTempdetailsBinding
import ru.itis.second_sem.di.components.AppComponent
import ru.itis.second_sem.utils.appComponent
import ru.itis.second_sem.utils.lazyViewModel

class TempDetailsFragment : BaseFragment(R.layout.fragment_tempdetails) {

    private val viewBinding: FragmentTempdetailsBinding by viewBinding(FragmentTempdetailsBinding::bind)

    private val vm: TempDetailsViewModel by lazyViewModel { stateHundle ->
        requireContext().appComponent().tempDetailsViewModel().create(
            longitude = arguments?.getFloat("Longitude_key") ?: 0f,
            latitude = arguments?.getFloat("Latitude_key") ?: 0f,
            savedStateHundle = stateHundle
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.start()
    }
}