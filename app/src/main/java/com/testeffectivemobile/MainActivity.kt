package com.testeffectivemobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.ui.MainNavigation
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestEffectiveMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController =
                        rememberNavController()

                    MainNavigation(
                        navHostController,
                        mainViewModel.mutableMockyCatalog,
                        mainViewModel.mainPrefStorage,
                        mainViewModel.mutableMockyCatalogSorting,
                        mainViewModel.mainDialog
                    ){

                        mainViewModel
                            .addNavHostController(navHostController)

                    }

                    mainViewModel.mainDialog
                        .collectAsStateWithLifecycle()
                        .value?.invoke()

                }
            }
        }

    }
}

