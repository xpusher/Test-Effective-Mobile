package com.testeffectivemobile.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.models.MockyContent
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.full.isSubclassOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(
    mutableNavRouteState: MutableStateFlow<MainAppNavState?>,
    mutableMockyContent: MutableStateFlow<MockyContent?>
) {
    val navController =
        rememberNavController()

    val rememberCoroutineScope=
        rememberCoroutineScope()

    val rememberStartDestination=
        remember {
                if (mutableNavRouteState.value==null)
                    MainAppNavState.ScreenAuth::class.java.simpleName
                else
                    mutableNavRouteState.value!!::class.java.simpleName

        }

            val routes = listOf(
                MainAppNavState.ScreenAuth::class.java.simpleName,
                MainAppNavState.CreateNav::class.java.simpleName,
                MainAppNavState.ScreenCatalog::class.java.simpleName,
                MainAppNavState.ScreenCatalogItem::class.java.simpleName,
            )

            NavHost(
                navController = navController,
                startDestination =rememberStartDestination
            ) {


                routes.forEach { destination ->
                    composable(destination)
                    {

                        when (destination
                        ) {
                            MainAppNavState.ScreenAuth::class.java.simpleName
                            ->{
                                ScreenAuth(mutableNavRouteState)
                            }
                            MainAppNavState.ScreenCatalog::class.java.simpleName
                            ->{
                                ScreenCatalog(
                                    mutableNavRouteState,
                                    mutableMockyContent)
                            }
                            MainAppNavState.ScreenCatalogItem::class.java.simpleName
                            ->{

                                ScreenCatalogItem(mutableMockyContent)
                            }
                        }
                    }
                }

            }

            LaunchedEffect("synchronize mutableNavRouteState"
            ){

                navController.currentBackStackEntryFlow
                    .collect {navBackStackEntry->
                        navBackStackEntry.destination.route
                            ?.let {
                                rememberCoroutineScope.launch {
                                    mutableNavRouteState.emit(
                                        MainAppNavState.valueOf(it)
                                    )
                                }
                            }
                    }

            }
            val state=mutableNavRouteState.collectAsStateWithLifecycle()
            when(state.value)
            {
                MainAppNavState.valueOf(navController.currentDestination?.route)
                ->{
                    "".toString()
                }

                MainAppNavState.ScreenAuth
                ->{
                    navController.navigate(MainAppNavState.ScreenAuth::class.java.simpleName)
                    {
                        popUpTo(0)
                    }
                }
                MainAppNavState.ScreenCatalog
                ->{
                    navController.navigate(MainAppNavState.ScreenCatalog::class.java.simpleName)
                    {
                        popUpTo(0)
                    }
                }
                MainAppNavState.ScreenCatalogItem
                ->{
                    navController.navigate(MainAppNavState.ScreenCatalogItem::class.java.simpleName)
                }
                else->{}
            }
}

sealed class MainAppNavState(var param:Any?=null){
    companion object {

        @JvmStatic private val map
            get() = MainAppNavState::class.nestedClasses
                .filter { klass -> klass.isSubclassOf(MainAppNavState::class) }
                .map { klass -> klass.objectInstance }
                .filterIsInstance<MainAppNavState>()
                .associateBy { value -> value::class.java.simpleName }

        @JvmStatic fun valueOf(value: String?) =
            if (value==null)
                null
            else requireNotNull(map[value]) {
                "${MainAppNavState::class.java.name}.$value"
            }

        @JvmStatic fun values() = map.values.toTypedArray()
    }

    data object ScreenAuth:MainAppNavState()
    data object ScreenCatalog:MainAppNavState()
    data object ScreenCatalogItem:MainAppNavState()
    data object CreateNav:MainAppNavState()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    TestEffectiveMobileTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                MainNavigation(
                    MutableStateFlow(MainAppNavState.ScreenAuth),
                    MutableStateFlow(MockyContent())
                )
            }
    }
}