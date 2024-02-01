package com.testeffectivemobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.testeffectivemobile.ui.MainNavigation

class MainActivity : ComponentActivity() {

    private val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MainNavigation(mainViewModel.mutableNavRouteState)

            mainViewModel.mainDialog
                .collectAsStateWithLifecycle()
                .value?.invoke()

        }

    }
}

