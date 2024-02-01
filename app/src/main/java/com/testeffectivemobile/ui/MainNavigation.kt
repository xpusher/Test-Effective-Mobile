package com.testeffectivemobile.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme

@Composable
fun MainNavigation(modifier: Modifier = Modifier
) {
    TestEffectiveMobileTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            val navController =
                rememberNavController()

            val routes = listOf(
                MainAppNavState.ScreenAuth::class.java.simpleName,
            )

            NavHost(
                navController = navController,
                startDestination = MainAppNavState.ScreenAuth::class.java.simpleName
            ) {


                routes.forEach { destination ->
                    composable(destination)
                    {
                        when (destination
                        ) {
                            MainAppNavState.ScreenAuth::class.java.simpleName->{
                                ScreenAuth(navController)
                            }
                        }
                    }
                }

            }
        }
    }
}

sealed class MainAppNavState{
    data object ScreenAuth:MainAppNavState()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestEffectiveMobileTheme {
        MainNavigation()
    }
}