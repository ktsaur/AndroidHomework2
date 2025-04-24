package ru.itis.second_sem.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.second_sem.R
import ru.itis.second_sem.presentation.screens.CurrentTempFragment
import ru.itis.second_sem.utils.appComponent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, CurrentTempFragment())
            .commit()

    }
}