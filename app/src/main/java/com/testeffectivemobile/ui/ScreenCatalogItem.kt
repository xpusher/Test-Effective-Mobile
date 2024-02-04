package com.testeffectivemobile.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.testeffectivemobile.models.MockyCatalog
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ScreenCatalogItem(
    id:String,
    mutableMockyCatalog: MutableStateFlow<MockyCatalog?>) {

    val mockyContentItem= remember {
        mutableMockyCatalog.value!!.item(id.toInt())
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
            ScreenCatalogItem(
                0.toString(),
                MutableStateFlow(MockyCatalog(mockyContentString)),
                )
        }
    }
}
