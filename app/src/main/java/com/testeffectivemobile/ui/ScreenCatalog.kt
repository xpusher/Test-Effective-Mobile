package com.testeffectivemobile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.R
import com.testeffectivemobile.models.MockyCatalog
import com.testeffectivemobile.models.MockyCatalogSorting
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenCatalog(
    navHostController: NavHostController,
    mutableMockyCatalog: MutableStateFlow<MockyCatalog?>,
    mutableMockyCatalogSorting: MutableStateFlow<MockyCatalogSorting>
) {

    val mockyContent=
        mutableMockyCatalog.collectAsStateWithLifecycle().value

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

        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.weight(1f)) {
                    DropdownRateCatalog(mutableMockyCatalogSorting)
                }
                Row(modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End) {
                    Icon(painter = painterResource(id = R.drawable.ic_filters_catalog),
                        contentDescription = null)

                    Text(
                        modifier = Modifier,
                        text = "Фильтр",
                    )

                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CarouselTags()
            }
            Row {
                LazyVerticalGrid(columns = GridCells.Fixed(2)){
                    items(count = mockyContent!!.items.length()) {position->
                        Box(modifier = Modifier
                            .aspectRatio(1f)
                            .clickable {
                                navHostController.navigate(
                                    "ScreenCatalogItem/$position"
                                )

                            }) {
                            mockyContent.item(position).ComposableMockyCatalogItem()
                        }
                    }
                }

            }
        }



    }
    Scaffold(
        topBar = {
            TopAppBar(stringResource(id = R.string.screen_catalog_title))
        },
        bottomBar = {
            BottomMenu(navHostController)
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {

            when{
                mockyContent==null
                -> {
                    LoadingCatalog()
                }
                mockyContent.isEmpty()
                ->{
                    EmptyCatalog()
                }
                else
                ->{
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
//                MutableStateFlow(MockyCatalog()))
            ScreenCatalog(
                rememberNavController(),
                MutableStateFlow(MockyCatalog(mockyContentString)),
                MutableStateFlow(MockyCatalogSorting.Default)
            )
        }
    }
}
