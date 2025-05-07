package ru.itis.auth.presentation.authorization

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.auth.R
import by.kirich1409.viewbindingdelegate.viewBinding
import by.kirich1409.viewbindingdelegate.viewBindingLazy
import ru.itis.auth.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    private val viewBinding: FragmentAuthorizationBinding by viewBinding(FragmentAuthorizationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.composeContainerId.setContent {
            AuthorizationRoute()
        }
    }
}