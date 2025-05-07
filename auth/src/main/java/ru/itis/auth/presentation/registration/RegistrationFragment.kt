package ru.itis.auth.presentation.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.auth.R
import ru.itis.auth.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    val viewBinding: FragmentRegistrationBinding by viewBinding(FragmentRegistrationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*viewBinding.composeContainerId.setContent {
            RegistrationRoute()
        }*/
    }
}