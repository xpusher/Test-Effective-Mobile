package com.testeffectivemobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.testeffectivemobile.ui.MainNavigation

class MainActivity : ComponentActivity() {

    val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainNavigation()

            mainViewModel.mainDialog
                .collectAsStateWithLifecycle()
                .value?.invoke()

        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.updateMocky()
    }
}

