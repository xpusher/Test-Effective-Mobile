package com.testeffectivemobile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.testeffectivemobile.R
import com.testeffectivemobile.models.MockyContent
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun ScreenCatalog(
    mutableNavRouteState: MutableStateFlow<MainAppNavState?>,
    mutableMockyContent: MutableStateFlow<MockyContent?>
) {

    @Composable
    fun EmptyCatalog(){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
        Text(text = stringResource(id = R.string.screen_catalog_text_empty))
        }


    }
    @Composable
    fun LoadingCatalog(){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
        Text(text = stringResource(id = R.string.screen_catalog_text_loading))
        }


    }
    @Composable
    fun ShowCatalog(){

        val mockyContent=
            mutableMockyContent.collectAsStateWithLifecycle().value

        val rememberCoroutineScope=
            rememberCoroutineScope()

        LazyVerticalGrid(columns = GridCells.Fixed(2)){
            items(count = mockyContent!!.items.length()) {position->
                Box(modifier = Modifier
                    .aspectRatio(1f)
                    .clickable {

                        MainAppNavState.ScreenCatalogItem.param =
                            position

                        rememberCoroutineScope.launch {
                            mutableNavRouteState.emit(MainAppNavState.ScreenCatalogItem)
                        }

                    }) {
                    Text(text = mockyContent.item(position).description)
                }
            }
        }


    }
    Scaffold(
        topBar = {
            TopAppBar(stringResource(id = R.string.screen_catalog_title))
        }) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {

            when{
                mutableMockyContent.value==null->
                    LoadingCatalog()
                mutableMockyContent.value!!.isEmpty()->{
                    EmptyCatalog()
                }
                else->{

                    ShowCatalog()
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenCatalogPreview(
) {
    TestEffectiveMobileTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

//            ScreenCatalog(
//                MutableStateFlow(null),
//                MutableStateFlow(MockyContent()))
            ScreenCatalog(
                MutableStateFlow(null),
                MutableStateFlow(MockyContent(mockyContentString)))
        }
    }
}
