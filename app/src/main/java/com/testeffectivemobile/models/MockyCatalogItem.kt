package com.testeffectivemobile.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testeffectivemobile.R
import com.testeffectivemobile.ui.ScreenCatalogItem
import com.testeffectivemobile.ui.mockyContentString
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONArray
import org.json.JSONObject

class MockyCatalogItem(stringJSONObject: String?=null):JSONObject(
    if (stringJSONObject==null)
        JSONObject().toString()
    else
        stringJSONObject
) {
    fun isEmpty(): Boolean {
        return !keys().hasNext()
    }

    val id
        get() = getString("id")
    val title
        get() = getString("title")
    val subtitle
        get() = getString("subtitle")

    //region price
    private val price:JSONObject
        get() = getJSONObject("price")
    val pricePrice:String
        get() = price.getString("price")+" $priceUnit"
    val priceDiscount:String
        get() = price.getString("discount")
    val pricePriceWithDiscount:String
        get() = price.getString("priceWithDiscount")+" $priceUnit"
    val priceUnit:String
        get() = price.getString("unit")
    //endregion

    //region feedback
    private val feedback:JSONObject
        get() = getJSONObject("feedback")

    val feedbackCount:Int
        get() = feedback.getInt("count")
    val feedbackRating:String
        get() = feedback.getString("rating")
    //endregion

    val available:Int
        get() = getInt("available")
    val description:String
        get() = getString("description")

    //region info
    private val info:JSONArray
        get() = getJSONArray("info")

    fun infoTitle(position:Int):String {
        return info.getJSONObject(position).getString("title")
    }
    fun infoValue(position: Int):String {
        return info.getJSONObject(position).getString("value")
    }


    //endregion

    val ingredients:String
        get() = getString("ingredients")

    @Composable
    fun ComposableMockyCatalogItem(
    ){
        Column(
            modifier = Modifier


        ) {
            //region header
            Row(
                modifier = Modifier
                    .aspectRatio(1.1f)

            ) {

                Row(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxSize()
                    ,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text ="to do Image")
                }
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_unfavorite),
                            contentDescription = null)
                    }
                    Row(
                        modifier = Modifier.weight(3f)
                    ) {

                    }
                }

            }
            //endregion
            //region footer
            Row(
                modifier = Modifier
                    .fillMaxSize()
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //region price
                    Row {
                        Text(
                            text = pricePrice,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough),
                            fontSize = 9.sp
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = pricePriceWithDiscount,
                            style = TextStyle(fontStyle = FontStyle.Normal),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,

                            )
                        Card(
                            modifier = Modifier.padding(3.dp),
                            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.button_container_color)),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                                text = priceDiscount+"%",
                                style = TextStyle( color = Color.White),
                                fontSize = 9.sp,

                                )
                        }
                    }
                    //endregion
                    //region title
                    Text(
                        text = title,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    //endregion
                    //region subtitle
                    Text(
                        modifier = Modifier,
                        text = subtitle,
                        fontSize = 10.sp,
                        lineHeight = 11.sp
                    )
                    //endregion
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Row(modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_rating),
                                contentDescription = null)

                            Text(
                                text = feedbackRating,
                                fontSize = 10.sp,
                                style = TextStyle(color = colorResource(id = R.color.rate_color))
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                ,
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Bottom
                        ) {

                        Image(painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = null)
                        }
                    }

                }
            }
            //endregion
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposableMockyCatalogItemPreview(
) {
    TestEffectiveMobileTheme(darkTheme = false) {
        MockyCatalog(mockyContentString).item(0).
        ComposableMockyCatalogItem()
    }
}
