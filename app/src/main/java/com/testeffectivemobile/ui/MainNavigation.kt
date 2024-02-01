package com.testeffectivemobile.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.full.isSubclassOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(mutableNavRouteState: MutableStateFlow<MainAppNavState?>
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
                MainAppNavState.CreateNav::class.java.simpleName,
                MainAppNavState.ScreenCatalog::class.java.simpleName,
            )

            NavHost(
                navController = navController,
                startDestination =
                if (mutableNavRouteState.value==null)
                    MainAppNavState.CreateNav::class.java.simpleName
                else
                    mutableNavRouteState.value!!::class.java.simpleName
            ) {


                routes.forEach { destination ->
                    composable(destination)
                    {

                        rememberCoroutineScope().launch {

                            mutableNavRouteState.emit(
                                MainAppNavState.valueOf(destination)
                            )

                        }

                        when (destination
                        ) {
                            MainAppNavState.ScreenAuth::class.java.simpleName
                            ->{
                                ScreenAuth(mutableNavRouteState)
                            }
                            MainAppNavState.ScreenCatalog::class.java.simpleName->{
                                ScreenCatalog()
                            }
                        }
                    }
                }

            }


            when(mutableNavRouteState.collectAsStateWithLifecycle().value)
            {
                MainAppNavState.ScreenAuth->{
                    navController.navigate(MainAppNavState.ScreenAuth::class.java.simpleName){
                        popUpTo(0)
                    }
                }
                MainAppNavState.ScreenCatalog->{
                    navController.navigate(MainAppNavState.ScreenCatalog::class.java.simpleName){
                        popUpTo(0)
                    }
                }
                else->{}
            }
        }
    }
}

sealed class MainAppNavState{
    companion object {

        @JvmStatic private val map
            get() = MainAppNavState::class.nestedClasses
                .filter { klass -> klass.isSubclassOf(MainAppNavState::class) }
                .map { klass -> klass.objectInstance }
                .filterIsInstance<MainAppNavState>()
                .associateBy { value -> value::class.java.simpleName }

        @JvmStatic fun valueOf(value: String) = requireNotNull(map[value]) {
            "${MainAppNavState::class.java.name}.$value"
        }

        @JvmStatic fun values() = map.values.toTypedArray()
    }

    data object ScreenAuth:MainAppNavState()
    data object ScreenCatalog:MainAppNavState()
    data object CreateNav:MainAppNavState()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    TestEffectiveMobileTheme {
        MainNavigation(MutableStateFlow(MainAppNavState.ScreenAuth))
    }
}