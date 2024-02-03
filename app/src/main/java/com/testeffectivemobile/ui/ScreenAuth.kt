@file:OptIn(ExperimentalMaterial3Api::class)

package com.testeffectivemobile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.testeffectivemobile.MainPrefStorage
import com.testeffectivemobile.R
import com.testeffectivemobile.models.UserProfile
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

@ExperimentalMaterial3Api
@Composable
fun ScreenAuth(
    navHostController: NavHostController,
    mainPrefStorage: MainPrefStorage,
    screenAuthViewModel:ScreenAuthViewModel= viewModel<ScreenAuthViewModel>()
        .apply {
                this@apply.navHostController=
                    navHostController
                this@apply.
                    addMainPrefStorage(mainPrefStorage)
        },

) {

    Scaffold(
        topBar = {
            TopAppBar(stringResource(id = R.string.screen_auth_title))
        }){innerPadding->

        val rememberCoroutineScope =
            rememberCoroutineScope()

        val firstName=
            screenAuthViewModel.userFirstName
                .collectAsStateWithLifecycle().value
        val lastName=
            screenAuthViewModel.userLastName
                .collectAsStateWithLifecycle().value
        val phone=
            screenAuthViewModel.userPhone
                .collectAsStateWithLifecycle().value

        val isValidFirstName =
            screenAuthViewModel.isValidUserFirstName
                .collectAsStateWithLifecycle().value
        val isValidLastName =
            screenAuthViewModel.isValidUserLastName
                .collectAsStateWithLifecycle().value
        val isValidPhone=
            screenAuthViewModel.isValidUserPhone
                .collectAsStateWithLifecycle()

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
            //region header top
            Row(modifier = Modifier.weight(1f)) {

            }
            //endregion
            //region first name
            Row {
                OutlinedTextField(
                    modifier = childModifier,
                    value = firstName,
                    label = { Text(text = stringResource(id = R.string.screen_auth_label_first_name))},
                    onValueChange = {
                        rememberCoroutineScope.launch {
                            screenAuthViewModel.userFirstName.emit(it)
                        }
                    },
                    colors = if (isValidFirstName)
                        textFieldColorsColorsValid()
                    else
                        textFieldColorsColorsInValid(),
                    trailingIcon = {
                        if (firstName.isNotEmpty()) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "clear text",
                                modifier = Modifier
                                    .clickable {
                                        rememberCoroutineScope.launch {
                                            screenAuthViewModel.userFirstName.emit("")
                                        }
                                    },

                                )
                        }
                    }
                )

            }
            //endregion
            Divider0()
            //region last name
            Row {
                OutlinedTextField(
                    modifier = childModifier,
                    value = lastName,
                    label = { Text(text = stringResource(id = R.string.screen_auth_label_last_name))},
                    onValueChange = {
                        rememberCoroutineScope.launch {
                            screenAuthViewModel.userLastName.emit(it)
                        }
                    },
                    colors = if (isValidLastName)
                        textFieldColorsColorsValid()
                    else
                        textFieldColorsColorsInValid(),
                    trailingIcon = {
                        if (lastName.isNotEmpty()) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "clear text",
                                modifier = Modifier
                                    .clickable {
                                        rememberCoroutineScope.launch {
                                            screenAuthViewModel.userLastName.emit("")
                                        }
                                    },

                                )
                        }
                    }
                )

            }
            //endregion
            Divider0()
            //region phone
            Row {
                OutlinedTextField(
                    modifier = childModifier,
                    value = phone,
                    label = {
                        Text(text = stringResource(id = R.string.screen_auth_label_phone))},
                    singleLine = true,
                    onValueChange = {
                        rememberCoroutineScope.launch {
                            screenAuthViewModel.userPhone.emit(it)
                        }
                    },
                    colors = if (isValidPhone.value)
                        textFieldColorsColorsValid()
                    else
                        textFieldColorsColorsInValid(),
                    placeholder = { Text(text = screenAuthViewModel.mask, color = Color.LightGray)},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone),
                    visualTransformation = MaskVisualTransformation(screenAuthViewModel.mask),
                    trailingIcon = {
                        if (phone.isNotEmpty()) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "clear text",
                                modifier = Modifier
                                    .clickable {
                                        rememberCoroutineScope.launch {
                                            screenAuthViewModel.userPhone.emit("")
                                        }
                                    },

                                )
                        }
                    }
                )

            }
            //endregion
            Divider0()
            //region button enter
            Row {
                Button(
                    modifier = childModifier.height(TextFieldDefaults.MinHeight),
                    onClick = {
                        screenAuthViewModel
                            .enter()
                    },
                    shape = RoundedCornerShape(dimensionResource(R.dimen.rounded0)),
                    enabled = isValidFirstName
                            && isValidLastName
                            && isValidPhone.value
                            && firstName.isNotEmpty()
                            && lastName.isNotEmpty()
                            && phone.isNotEmpty()
                    ,
                    colors = buttonColor()
                ) {
                    Text(
                        text = stringResource(id = R.string.screen_auth_button),
                        color = buttonColorText()
                        )
                }
            }
            //endregion
            //region footer
            Row(modifier = Modifier.weight(1.5f),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = stringResource(id = R.string.screen_auth_footer),
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp
                )
            }
            //endregion
        }
    }

}

class ScreenAuthViewModel:ViewModel(
){
    lateinit var navHostController: NavHostController
    lateinit var userProfile:UserProfile
    lateinit var mainPrefStorage:MainPrefStorage
    fun enter() {
        navHostController.navigate(
            route= routes[2]
        ){
            popUpTo(0)
        }
    }

    val mask="+7 ### ###-##-##"
    val userFirstName=
        MutableStateFlow("")
    val userLastName=
        MutableStateFlow("")
    val userPhone=
        MutableStateFlow("")

    val isValidUserFirstName=
        MutableStateFlow(true)
    val isValidUserLastName=
        MutableStateFlow(true)
    val isValidUserPhone=
        MutableStateFlow(true)

    private val regexPhone=
        Regex("[0-9[ ][-][+]]")
    private val regexCyrillic=
        Regex("[а-я[А-Я]]")

    private fun storeUserProfile(){

        userProfile.firstName=
            userFirstName.value
        userProfile.lastName=
            userLastName.value
        userProfile.phone=
            userPhone.value

        mainPrefStorage.setField(
                MainPrefStorage.Keys.UserProfile,
                userProfile)

    }

    fun addMainPrefStorage(mainPrefStorage: MainPrefStorage) {
        this@ScreenAuthViewModel.mainPrefStorage=mainPrefStorage
        userProfile=
            mainPrefStorage.getField<UserProfile>(MainPrefStorage.Keys.UserProfile)!!
//                    if (userProfile.isValid)
//                        enter()
//                    else {
        userFirstName.value = userProfile.firstName ?: ""
        userLastName.value = userProfile.lastName ?: ""
        userPhone.value = userProfile.phone ?: ""

        //region first
        viewModelScope.launch {
            userFirstName.collect{
                isValidUserFirstName.emit(true)
                for ( i in it.indices) {
                    if (!it[i].toString().contains(regexCyrillic)) {
                        viewModelScope.launch {
                            isValidUserFirstName.emit(false)
                        }
                        delay(300)
                        userFirstName.value =
                            it.removeRange(i, i + 1)
                    }
                }
                userFirstName.value=
                    userFirstName.value.replaceFirstChar {capitChar->
                        if (capitChar.isLowerCase())
                            capitChar.titlecase(
                                Locale.getDefault())
                        else
                            capitChar.toString()
                    }

                storeUserProfile()

            }
        }
        //endregion

        //region last
        viewModelScope.launch {
            userLastName.collect{
                isValidUserLastName.emit(true)
                for ( i in it.indices) {
                    if (!it[i].toString().contains(regexCyrillic)) {
                        viewModelScope.launch {
                            isValidUserLastName.emit(false)
                        }
                        delay(300)
                        userLastName.value =
                            it.removeRange(i, i + 1)
                    }
                }
                userLastName.value=userLastName.value.replaceFirstChar {capitChar->
                    if (capitChar.isLowerCase())
                        capitChar.titlecase(
                            Locale.getDefault())
                    else
                        capitChar.toString()
                }

                storeUserProfile()

            }
        }
        //endregion

        //region phone
        viewModelScope.launch {
            userPhone.collect{

                for ( i in it.indices) {
                    val currentChar=it[i]
                    val removeChar =
                        if (i<mask.count { tmp-> tmp.toString()=="#" }) {

                            !currentChar
                                .toString()
                                .contains(regexPhone)
                                    ||
                                    (i == 0 && currentChar == mask[1])

                        }
                        else{
                            true
                        }
                    if (removeChar) {
                        viewModelScope.launch {
                            isValidUserPhone.emit(false)
                        }
                        delay(300)
                        userPhone.value =
                            it.removeRange(i, i + 1)
                        isValidUserPhone.emit(false)
                    }
                }

                isValidUserPhone.emit(
                    mask.count { tmp-> tmp.toString()=="#" }
                            ==
                            userPhone.value.length
                )

                storeUserProfile()

            }
        }
        //endregion

//                    }

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


            ScreenAuth(
                navHostController = rememberNavController(),
                mainPrefStorage = MainPrefStorage(LocalContext.current))
        }
    }
}
