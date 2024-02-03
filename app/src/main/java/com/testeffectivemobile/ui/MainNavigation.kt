package com.testeffectivemobile.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.MainPrefStorage
import com.testeffectivemobile.models.MockyContent
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(
    navHostController: NavHostController,
    mutableMockyContent: MutableStateFlow<MockyContent?>,
    mainPrefStorage: MainPrefStorage,
    onCreateNav:()->Unit
) {

    NavHost(
        navController = navHostController,
        startDestination = routes[routes.indexOf("OnCreateNav")]
    ) {

        routes.forEach { destination ->
            composable(destination)
            {
                when (destination
                ) {
                    routes[routes.indexOf("OnCreateNav")]
                            ->{
                                onCreateNav.invoke()
                            }
                    routes[routes.indexOf("ScreenAuth")]
                    ->{
                        ScreenAuth(
                            navHostController=navHostController,
                            mainPrefStorage = mainPrefStorage)
                    }
                    routes[routes.indexOf("ScreenCatalog")]
                    ->{
                        ScreenCatalog(
                            navHostController=navHostController,
                            mutableMockyContent)
                    }
                    routes[routes.indexOf("ScreenCatalogItem/{id}")]
                    ->{

                        ScreenCatalogItem(
                            id=it.arguments!!.getString("id")!!,
                            mutableMockyContent=mutableMockyContent)
                    }
                    routes[routes.indexOf("ScreenMain")]
                    ->{
                        ScreenMain(navHostController=navHostController)
                    }
                    routes[routes.indexOf("ScreenBasket")]
                    ->{
                        ScreenBasket(navHostController=navHostController)
                    }

                }
            }
        }

    }

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
                    rememberNavController(),
                    MutableStateFlow(MockyContent()),
                    MainPrefStorage(LocalContext.current)
                ){}
            }
    }
}