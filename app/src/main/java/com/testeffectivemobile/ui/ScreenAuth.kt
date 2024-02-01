package com.testeffectivemobile.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAuth(
    navController: NavHostController,
    screenAuthViewModel:ScreenAuthViewModel= viewModel()
) {
    val rememberCoroutineScope =
        rememberCoroutineScope()


    OutlinedTextField(
        modifier = Modifier.wrapContentHeight().padding(10.dp),
        value = screenAuthViewModel.userFirstName.collectAsStateWithLifecycle().value,
        onValueChange = {
            rememberCoroutineScope.launch {
                screenAuthViewModel.userFirstName.emit(it)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        )
    )

}

class ScreenAuthViewModel:ViewModel(){
    val userFirstName=
        MutableStateFlow("FirstName")

    init {
        viewModelScope.launch {
            userFirstName.collect{
                it.toString()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ScreenAuthPreview(
) {
    TestEffectiveMobileTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController =
                rememberNavController()

            ScreenAuth(navController)
        }
    }
}
