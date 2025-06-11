package ru.itis.second_sem.presentation.screens.customView

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.second_sem.R
import ru.itis.second_sem.databinding.FragmentCustomViewBinding
import ru.itis.second_sem.presentation.base.BaseFragment

class CustomViewFragment: BaseFragment(R.layout.fragment_custom_view) {

    val binding: FragmentCustomViewBinding by viewBinding(FragmentCustomViewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val values = listOf(52f, 60f, 70f, 80f)
        val colors = listOf(
            Color.parseColor("#4CAF50"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#FF9800")
        )

        binding.customView.setSectorsData(values, colors)
    }
}