@file:OptIn(ExperimentalMaterial3Api::class)

package com.testeffectivemobile.ui

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.testeffectivemobile.R

@Composable
fun Divider0(){
    Divider(
        thickness = dimensionResource(R.dimen.padding0),
        color = Color.Transparent
    )
}
@Composable
fun TopAppBar(stringTitle:String){
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringTitle)
        },
    )

}