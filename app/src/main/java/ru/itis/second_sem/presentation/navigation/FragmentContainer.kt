package ru.itis.second_sem.presentation.navigation

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager

@Composable
fun FragmentContainer(
    modifier: Modifier,
    fragmentClass: Class<out Fragment>
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            FragmentContainerView(context).apply {
                id = View.generateViewId()
                val fragmentManager = (context as FragmentActivity).supportFragmentManager
                val fragment = fragmentManager.fragmentFactory.instantiate(
                    context.classLoader,
                    fragmentClass.name
                )
                fragmentManager.beginTransaction()
                    .replace(id, fragment)
                    .commit()
            }
        }
    )
}