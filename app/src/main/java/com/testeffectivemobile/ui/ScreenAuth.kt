@file:OptIn(ExperimentalMaterial3Api::class)

package com.testeffectivemobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.R
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun ScreenAuth(
    mutableNavRouteState: MutableStateFlow<MainAppNavState?>,
    screenAuthViewModel:ScreenAuthViewModel= viewModel()
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.screen_auth_title))
                },
            )
        }){innerPadding->

        val rememberCoroutineScope =
            rememberCoroutineScope()

        val colorsValid=
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            )

        val colorsInValid=
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.errorContainer,
                focusedContainerColor = MaterialTheme.colorScheme.errorContainer,
            )

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(
                    start = dimensionResource(R.dimen.padding0),
                    end = dimensionResource(R.dimen.padding0)
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val childModifier=Modifier.fillMaxWidth()
            Row(modifier = Modifier.weight(1f)) {

            }
            Row {
                OutlinedTextField(
                    modifier = childModifier,
                    value = screenAuthViewModel.userFirstName
                        .collectAsStateWithLifecycle().value,
                    onValueChange = {
                        rememberCoroutineScope.launch {
                            screenAuthViewModel.userFirstName.emit(it)
                        }
                    },
                    colors = if (screenAuthViewModel.isValidUserFirstName.value)
                        colorsValid
                    else
                        colorsInValid
                )

            }
            Divider0()
            Row {
                OutlinedTextField(
                    modifier = childModifier,
                    value = screenAuthViewModel.userLastName
                        .collectAsStateWithLifecycle().value,
                    onValueChange = {
                        rememberCoroutineScope.launch {
                            screenAuthViewModel.userLastName.emit(it)
                        }
                    },
                    colors = if (screenAuthViewModel.isValidUserLastName.value)
                        colorsValid
                    else
                        colorsInValid
                )

            }
            Divider0()
            Row {
                OutlinedTextField(
                    modifier = childModifier,
                    value = screenAuthViewModel.userPhone
                        .collectAsStateWithLifecycle().value,
                    onValueChange = {
                        rememberCoroutineScope.launch {
                            screenAuthViewModel.userPhone.emit(it)
                        }
                    },
                    colors = if (screenAuthViewModel.isValidUserPhone.value)
                        colorsValid
                    else
                        colorsInValid
                )

            }
            Divider0()
            Row {
                Button(
                    modifier = childModifier.height(TextFieldDefaults.MinHeight),
                    onClick = {
                        rememberCoroutineScope.launch {
                            mutableNavRouteState.emit(MainAppNavState.ScreenCatalog)
                        }
                    },
                    shape = RoundedCornerShape(dimensionResource(R.dimen.rounded0))
                ) {

                }
            }
            Row(modifier = Modifier.weight(1.5f),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = "asas")
            }
        }
    }




}

class ScreenAuthViewModel:ViewModel(
){


    val userFirstName=
        MutableStateFlow("FirstName")
    val userLastName=
        MutableStateFlow("LastName")
    val userPhone=
        MutableStateFlow("+7 922 00-00-00")

    val isValidUserFirstName=
        MutableStateFlow(true)
    val isValidUserLastName=
        MutableStateFlow(true)
    val isValidUserPhone=
        MutableStateFlow(true)

    init {
        viewModelScope.launch {
            userFirstName.collect{
                it.toString()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ScreenAuthPreview(
) {
    TestEffectiveMobileTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            ScreenAuth(MutableStateFlow(null))
        }
    }
}
