package com.testeffectivemobile.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.testeffectivemobile.models.MockyContent
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ScreenCatalogItem(mutableMockyContent: MutableStateFlow<MockyContent?>) {

    val mockyContentItem= remember {
        mutableMockyContent.value!!.item(
            "${MainAppNavState.ScreenCatalogItem.param}".toInt()
        )
    }
        Text(text = mockyContentItem.description)
}

@Preview(showBackground = true)
@Composable
fun ScreenCatalogItemPreview(
) {
    TestEffectiveMobileTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainAppNavState.ScreenCatalogItem.param=1
            ScreenCatalogItem(
                MutableStateFlow(MockyContent(mockyContentString)),
                )
        }
    }
}
