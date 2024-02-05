package com.testeffectivemobile.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.models.MockyCatalog
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ScreenCatalogItem(
    navHostController: NavHostController,
    id: String,
    mutableMockyCatalog: MutableStateFlow<MockyCatalog?>,
    mainDialog: MutableStateFlow<@Composable() (() -> Unit)?>
) {

    val mockyContentItem= remember {
        mutableMockyCatalog.value!!.item(id.toInt())
    }
    Scaffold(
        topBar = {
            TopAppBar1(navHostController)
        },
        bottomBar = {}
    ){innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            mockyContentItem.ComposableMockyCatalogItemFull()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ScreenCatalogItemPreview(
) {
    TestEffectiveMobileTheme(darkTheme = false) {
            ScreenCatalogItem(
                rememberNavController(),
                0.toString(),
                MutableStateFlow(MockyCatalog(mockyContentString)),
                MutableStateFlow<(@Composable ()->Unit)?>(null),
                )
    }
}
