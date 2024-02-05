@file:OptIn(ExperimentalMaterial3Api::class)

package com.testeffectivemobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.R
import com.testeffectivemobile.models.MockyCatalogSorting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


val routes = listOf(
    "OnCreateNav",
    "ScreenAuth",
    "ScreenMain",
    "ScreenCatalog",
    "ScreenBasket",
    "ScreenStock",
    "ScreenProfile",
    "ScreenCatalogItem/{id}",
)

@Composable
fun BottomMenu(navHostController: NavHostController
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background
    ) {

        routes.forEach {

            val selected=
                navHostController.currentBackStackEntry?.destination?.route?.startsWith(
                    it.split("/")[0]
                )?:false

            val color=
                if (selected)
                    Color.Red
                else
                    Color.DarkGray

            @Composable
            fun CreateLabel(stringTitle:String){
                Text(
                    text = stringTitle,
                    color=color,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )

            }

            when(it){
                routes[routes.indexOf("ScreenCatalog")]->{
                    BottomNavigationItem(
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_bottom_menu_catalog),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(color)
                            )
                        },
                        onClick = {
                            navHostController.navigate(it){popUpTo(0)}
                        },
                        label = {
                            CreateLabel(stringResource(id = R.string.screen_catalog_title))
                        },
                        selected = selected,
                    )

                }
                routes[routes.indexOf("ScreenMain")]->{
                    BottomNavigationItem(
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_bottom_menu_main),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(color)
                            )
                        },
                        onClick = {
                            navHostController.navigate(it){popUpTo(0)}
                        },
                        label = {
                            CreateLabel(stringResource(id = R.string.screen_main_title))
                        },
                        selected = selected,
                    )

                }
                routes[routes.indexOf("ScreenBasket")]->{
                    BottomNavigationItem(
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_bottom_menu_basket),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(color)
                            )
                        },
                        onClick = {
                            navHostController.navigate(it){popUpTo(0)}
                        },
                        label = {
                            CreateLabel(stringResource(id = R.string.screen_basket_title))
                        },
                        selected = selected,
                    )

                }
                routes[routes.indexOf("ScreenStock")]->{
                    BottomNavigationItem(
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_bottom_menu_stock),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(color)
                            )
                        },
                        onClick = {
                            navHostController.navigate(it){popUpTo(0)}
                        },
                        label = {
                            CreateLabel(stringResource(id = R.string.screen_stock_title))
                        },
                        selected = selected,
                    )

                }
                routes[routes.indexOf("ScreenProfile")]->{
                    BottomNavigationItem(
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_bottom_menu_profile),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(color)
                            )
                        },
                        onClick = {
                            navHostController.navigate(it){popUpTo(0)}
                        },
                        label = {
                            CreateLabel(stringResource(id = R.string.screen_profil_title))
                        },
                        selected = selected,
                    )

                }
            }

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun BottomMenuPreview(
) {
    BottomMenu(rememberNavController())
}

@Composable
fun DropdownRateCatalog(mutableMockyCatalogSorting: MutableStateFlow<MockyCatalogSorting>) {

    val rememberCoroutineScope=
        rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }

    val items = listOf(
        stringResource(id = R.string.rate_catalog_0),
        stringResource(id = R.string.rate_catalog_1),
        stringResource(id = R.string.rate_catalog_3))

    var selectedIndex by remember { mutableStateOf(when(mutableMockyCatalogSorting.value){
        MockyCatalogSorting.Default->0
        MockyCatalogSorting.Decrease->1
        MockyCatalogSorting.Increase->2
    }) }

    Box(modifier = Modifier) {

        Row(                modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { expanded = true }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_rate_catalog),
                contentDescription = null)
            Text(
                items[selectedIndex],
            )

            Icon(
                modifier = Modifier.padding(5.dp),
                painter = painterResource(id = R.drawable.ic_rate_catalog_click),
                contentDescription = null)

        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()

        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false

                    rememberCoroutineScope.launch {
                        mutableMockyCatalogSorting.emit(
                            when(selectedIndex){
                                0->MockyCatalogSorting.Default
                                1->MockyCatalogSorting.Decrease
                                2->MockyCatalogSorting.Increase
                                else->throw Exception()
                            }
                        )
                    }


                },
                    text =  {
                        Text(text = items[index] )
                    }
                )
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun DropdownRateCatalogPreview(
) {
    DropdownRateCatalog(MutableStateFlow(MockyCatalogSorting.Default))
}

@Composable
fun CarouselTags(){

    var tags= listOf(
        "Смотреть все",
        "Лицо",
        "Тело",
        "Загар",
        "Маски",
    )

    LazyHorizontalGrid(
        modifier = Modifier.height(70.dp),
        rows = GridCells.Fixed(1)){
        items(tags.size){
            Card(modifier = Modifier
                .padding(start = 5.dp)
                .wrapContentHeight()) {
                Text(
                    modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp, end = 15.dp),
                    text = tags[it])

            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CarouselTagsPreview(
) {
    CarouselTags()
}

@Composable
fun UnderConstruction(){
    Text(text = stringResource(id = R.string.common_undeconstraction))
}

@Composable
fun textFieldColorsColorsValid(): TextFieldColors
{
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        unfocusedContainerColor = colorResource(id = R.color.text_field_container_color),
        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
    )
}

@Composable
fun textFieldColorsColorsInValid(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        unfocusedContainerColor = colorResource(id = R.color.text_field_container_color),
        focusedContainerColor = MaterialTheme.colorScheme.errorContainer,
    )
}

@Composable
fun buttonColor():ButtonColors{
    return ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.button_container_color),
            disabledContainerColor = colorResource(id = R.color.button_container_color_disable),
    )
}

@Composable
fun buttonColorText():Color{
    return Color.White
}

@Composable
fun Divider0(){
    Divider(
        thickness = dimensionResource(R.dimen.padding0),
        color = Color.Transparent
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(stringTitle:String){

    CenterAlignedTopAppBar(
        title = {
            Text(text = stringTitle)
        },
    )

}
class MaskVisualTransformation(private val mask: String) : VisualTransformation {

    private val specialSymbolsIndices = mask.indices.filter { mask[it] != '#' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == '#') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == '#' }
        }
    }
}
val mockyContentString:String
    get() = "{\n" +
        "  \"items\": [\n" +
        "    {\n" +
        "      \"id\": \"cbf0c984-7c6c-4ada-82da-e29dc698bb50\",\n" +
        "      \"title\": \"ESFOLIO\",\n" +
        "      \"subtitle\": \"Лосьон для тела `ESFOLIO` COENZYME Q10 Увлажняющий 500 мл\",\n" +
        "      \"price\": {\n" +
        "        \"price\": \"749\",\n" +
        "        \"discount\": 35,\n" +
        "        \"priceWithDiscount\": \"489\",\n" +
        "        \"unit\": \"₽\"\n" +
        "      },\n" +
        "      \"feedback\": {\n" +
        "        \"count\": 51,\n" +
        "        \"rating\": 4.5\n" +
        "      },\n" +
        "      \"tags\": [\n" +
        "        \"body\"\n" +
        "      ],\n" +
        "      \"available\": 100,\n" +
        "      \"description\": \"Лосьон для тела `ESFOLIO` COENZYME Q10 Увлажняющий содержит минеральную воду и соду, способствует глубокому очищению пор от различных загрязнений, контроллирует работу сальных желез, сужает поры. Обладает мягким антиептическим действием, не пересушивает кожу. Минеральная вода тонизирует и смягчает кожу.\",\n" +
        "      \"info\": [\n" +
        "        {\n" +
        "          \"title\": \"Артикул товара\",\n" +
        "          \"value\": \"441187\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Область использования\",\n" +
        "          \"value\": \"Тело\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Страна производства\",\n" +
        "          \"value\": \"Франция\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"ingredients\": \"Glycerin Palmitic Acid, Stearic Acid, Capric Acid, Sodium Benzoate\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"54a876a5-2205-48ba-9498-cfecff4baa6e\",\n" +
        "      \"title\": \"A`PIEU\",\n" +
        "      \"subtitle\": \"Пенка для умывания A`PIEU` `DEEP CLEAN` 200  мл \",\n" +
        "      \"price\": {\n" +
        "        \"price\": \"899\",\n" +
        "        \"discount\": 39,\n" +
        "        \"priceWithDiscount\": \"549\",\n" +
        "        \"unit\": \"₽\"\n" +
        "      },\n" +
        "      \"feedback\": {\n" +
        "        \"count\": 4,\n" +
        "        \"rating\": 4.3\n" +
        "      },\n" +
        "      \"tags\": [\n" +
        "        \"face\"\n" +
        "      ],\n" +
        "      \"available\": 30,\n" +
        "      \"description\": \"Пенка для лица Глубокое очищение содержит минеральную воду и соду, способствует глубокому очищению пор от различных загрязнений, контроллирует работу сальных желез, сужает поры. Обладает мягким антиептическим действием, не пересушивает кожу. Минеральная вода тонизирует и смягчает кожу.\",\n" +
        "      \"info\": [\n" +
        "        {\n" +
        "          \"title\": \"Артикул товара\",\n" +
        "          \"value\": \"133987\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Область использования\",\n" +
        "          \"value\": \"Лицо\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Страна производства\",\n" +
        "          \"value\": \"Республика Корея\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"ingredients\": \"Water, Glycerin Palmitic Acid, Stearic Acid, Myristic Acid, Potassium Hydroxide, Lauric Acid, Cocamidopropyl Betaine, Tea-Lauryl Sulfate, Phenoxyethanol, Sodium Chloride, Acrylates/C10-30 Alkyl Acrylate Crosspolymer, Arachidic Acid, Fragrance, Cellulose Gum, Disodium Edta, Capric Acid, Sodium Benzoate\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"75c84407-52e1-4cce-a73a-ff2d3ac031b3\",\n" +
        "      \"title\": \"DECO.\",\n" +
        "      \"subtitle\": \"Салфетки для лица `DECO.` матирующие с экстрактом зеленого чая 100 шт\",\n" +
        "      \"price\": {\n" +
        "        \"price\": \"329\",\n" +
        "        \"discount\": 40,\n" +
        "        \"priceWithDiscount\": \"199\",\n" +
        "        \"unit\": \"₽\"\n" +
        "      },\n" +
        "      \"feedback\": {\n" +
        "        \"count\": 15,\n" +
        "        \"rating\": 4.0\n" +
        "      },\n" +
        "      \"tags\": [\n" +
        "        \"face\"\n" +
        "      ],\n" +
        "      \"available\": 22,\n" +
        "      \"description\": \"Салфетки для лица `DECO.` матирующие с экстрактом зеленого чая содержит минеральную воду и соду, способствует глубокому очищению пор от различных загрязнений, контроллирует работу сальных желез, сужает поры. Обладает мягким антиептическим действием, не пересушивает кожу. Минеральная вода тонизирует и смягчает кожу.\",\n" +
        "      \"info\": [\n" +
        "        {\n" +
        "          \"title\": \"Артикул товара\",\n" +
        "          \"value\": \"324567\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Область использования\",\n" +
        "          \"value\": \"Лицо\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Страна производства\",\n" +
        "          \"value\": \"Республика Конго\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"ingredients\": \"Myristic Acid, Potassium Hydroxide, Lauric Acid, Cocamidopropyl Betaine, Tea-Lauryl Sulfate, Phenoxyethanol, Sodium Chloride, Acrylates/C10-30 Alkyl Acrylate Crosspolymer, Arachidic Acid, Fragrance, Cellulose Gum, Disodium Edta, Capric Acid, Sodium Benzoate\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"16f88865-ae74-4b7c-9d85-b68334bb97db\",\n" +
        "      \"title\": \"LP CARE\",\n" +
        "      \"subtitle\": \"Маска-перчатки для рук `LP-CARE` увлажняющая 40 мл\",\n" +
        "      \"price\": {\n" +
        "        \"price\": \"169\",\n" +
        "        \"discount\": 42,\n" +
        "        \"priceWithDiscount\": \"99\",\n" +
        "        \"unit\": \"₽\"\n" +
        "      },\n" +
        "      \"feedback\": {\n" +
        "        \"count\": 140,\n" +
        "        \"rating\": 4.6\n" +
        "      },\n" +
        "      \"tags\": [\n" +
        "        \"mask\",\n" +
        "        \"body\"\n" +
        "      ],\n" +
        "      \"available\": 51,\n" +
        "      \"description\": \"Маска-перчатки для рук `LP-CARE` увлажняющая способствует глубокому очищению пор от различных загрязнений\",\n" +
        "      \"info\": [\n" +
        "        {\n" +
        "          \"title\": \"Артикул товара\",\n" +
        "          \"value\": \"558899\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Область использования\",\n" +
        "          \"value\": \"Тело\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Страна производства\",\n" +
        "          \"value\": \"Республика Корея\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"ingredients\": \"Water, Glycerin Palmitic Acid, Cocamidopropyl Betaine, Tea-Lauryl Sulfate, Phenoxyethanol, Sodium Chloride, Acrylates/C10-30 Alkyl Acrylate Crosspolymer, Arachidic Acid, Fragrance, Cellulose Gum, Disodium Edta, Capric Acid, Sodium Benzoate\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"26f88856-ae74-4b7c-9d85-b68334bb97db\",\n" +
        "      \"title\": \"EVO\",\n" +
        "      \"subtitle\": \"Молочко для тела Пантенол 250 мл\",\n" +
        "      \"price\": {\n" +
        "        \"price\": \"299\",\n" +
        "        \"discount\": 20,\n" +
        "        \"priceWithDiscount\": \"239\",\n" +
        "        \"unit\": \"₽\"\n" +
        "      },\n" +
        "      \"feedback\": {\n" +
        "        \"count\": 99,\n" +
        "        \"rating\": 3.2\n" +
        "      },\n" +
        "      \"tags\": [\n" +
        "        \"body\"\n" +
        "      ],\n" +
        "      \"available\": 73,\n" +
        "      \"description\": \"Увлажняющее молочко с 2%-ным содержанием Декспантенола предназначено для ежедневного ухода за кожей, в том числе за очень сухой и склонной к шелушению. При ежедневном применении идеально увлажненная кожа остается нежной, гладкой и бархатистой в течение всего дня.\",\n" +
        "      \"info\": [\n" +
        "        {\n" +
        "          \"title\": \"Артикул товара\",\n" +
        "          \"value\": \"111899\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Область использования\",\n" +
        "          \"value\": \"Тело\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"ingredients\": \"Water, Sorbitol, Isopropyl Palmitate, Panthenol, Cetearyl Alcohol (and) Potassium Cetyl Phosphate\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"15f88865-ae74-4b7c-9d81-b78334bb97db\",\n" +
        "      \"title\": \"Aravia Professional\",\n" +
        "      \"subtitle\": \"Маска с поросуживающим эффектом Aravia Professional Post-Acne Balance Mask 100 мл\",\n" +
        "      \"price\": {\n" +
        "        \"price\": \"199\",\n" +
        "        \"discount\": 50,\n" +
        "        \"priceWithDiscount\": \"99\",\n" +
        "        \"unit\": \"₽\"\n" +
        "      },\n" +
        "      \"feedback\": {\n" +
        "        \"count\": 111,\n" +
        "        \"rating\": 3.8\n" +
        "      },\n" +
        "      \"tags\": [\n" +
        "        \"mask\",\n" +
        "        \"face\"\n" +
        "      ],\n" +
        "      \"available\": 14,\n" +
        "      \"description\": \"Маска Aravia Professional увлажняющая способствует глубокому очищению пор от различных загрязнений\",\n" +
        "      \"info\": [\n" +
        "        {\n" +
        "          \"title\": \"Артикул товара\",\n" +
        "          \"value\": \"158811\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Область использования\",\n" +
        "          \"value\": \"Лицо\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Страна производства\",\n" +
        "          \"value\": \"Республика Узбекистан\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"ingredients\": \"Cocamidopropyl Betaine, Tea-Lauryl Sulfate, Phenoxyethanol, Sodium Chloride, Acrylates/C10-30 Alkyl Acrylate Crosspolymer, Arachidic Acid, Fragrance, Cellulose Gum, Disodium Edta, Capric Acid, Sodium Benzoate\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"88f88865-ae74-4b7c-9d81-b78334bb97db\",\n" +
        "      \"title\": \"Floresan\",\n" +
        "      \"subtitle\": \"Масло водостойкое Floresan Активатор загара SPF 20 150 мл\",\n" +
        "      \"price\": {\n" +
        "        \"price\": \"499\",\n" +
        "        \"discount\": 50,\n" +
        "        \"priceWithDiscount\": \"199\",\n" +
        "        \"unit\": \"₽\"\n" +
        "      },\n" +
        "      \"feedback\": {\n" +
        "        \"count\": 16,\n" +
        "        \"rating\": 4.8\n" +
        "      },\n" +
        "      \"tags\": [\n" +
        "        \"suntan\"\n" +
        "      ],\n" +
        "      \"available\": 14,\n" +
        "      \"description\": \"Масло водостойкое Активатор загара SPF 20\",\n" +
        "      \"info\": [\n" +
        "        {\n" +
        "          \"title\": \"Артикул товара\",\n" +
        "          \"value\": \"333811\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Страна производства\",\n" +
        "          \"value\": \"Республика Узбекистан\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"ingredients\": \"вода питьевая; масло парфюмерное; масло соевое; циклометикон; этилгексил метоксициннамат; изопропил пальмитат; экстракт моркови; масло Ши; пропиленгликоль; витамин Е; натрия хлорид; гидантоин; ?-каротин; Д-пантенол; парфюмерная композиция; лимонен; амилциннамал\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": \"55f58865-ae74-4b7c-9d81-b78334bb97db\",\n" +
        "      \"title\": \"Floresan\",\n" +
        "      \"subtitle\": \"Масло для загара Floresan Гавайское\",\n" +
        "      \"price\": {\n" +
        "        \"price\": \"99\",\n" +
        "        \"discount\": 50,\n" +
        "        \"priceWithDiscount\": \"49\",\n" +
        "        \"unit\": \"₽\"\n" +
        "      },\n" +
        "      \"feedback\": {\n" +
        "        \"count\": 2,\n" +
        "        \"rating\": 4.9\n" +
        "      },\n" +
        "      \"tags\": [\n" +
        "        \"suntan\"\n" +
        "      ],\n" +
        "      \"available\": 44,\n" +
        "      \"description\": \"От морщин; смягчающий; усиливающий загар\",\n" +
        "      \"info\": [\n" +
        "        {\n" +
        "          \"title\": \"Артикул товара\",\n" +
        "          \"value\": \"158811\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"title\": \"Страна производства\",\n" +
        "          \"value\": \"Республика Узбекистан\"\n" +
        "        }\n" +
        "      ],\n" +
        "      \"ingredients\": \"Glycine Soja Oil (масло соевое), Prunus Amygdalus Dulcis Oil (Масло миндальное), Cocos Nucifera Oil (Масло кокосовое), Tocopheryl Acetate (Витамин Е), Butyrospermum Parkii Oil (Масло Ши)\"\n" +
        "    }\n" +
        "  ]\n" +
        "}"