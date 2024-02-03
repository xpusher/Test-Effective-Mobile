package com.testeffectivemobile.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.MainPrefStorage
import com.testeffectivemobile.models.MockyContent
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.full.isSubclassOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(
    navHostController: NavHostController,
    mutableMockyContent: MutableStateFlow<MockyContent?>,
    mainPrefStorage: MainPrefStorage
) {

    NavHost(
        navController = navHostController,
        startDestination = routes[0]
    ) {

        routes.forEach { destination ->
            composable(destination)
            {
                when (destination
                ) {
                    routes[1]
                    ->{
                        ScreenAuth(
                            navHostController=navHostController,
                            mainPrefStorage = mainPrefStorage)
                    }
                    routes[2]
                    ->{
                        ScreenCatalog(
                            navHostController=navHostController,
                            mutableMockyContent)
                    }
                    routes[3]
                    ->{

                        ScreenCatalogItem(
                            id=it.arguments!!.getString("id")!!,
                            mutableMockyContent=mutableMockyContent)
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
                )
            }
    }
}