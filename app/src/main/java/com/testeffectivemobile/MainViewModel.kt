package com.testeffectivemobile

import android.app.Application
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.testeffectivemobile.models.MockyContent
import com.testeffectivemobile.models.UserProfile
import com.testeffectivemobile.ui.errorUpdateMocky
import com.testeffectivemobile.ui.routes
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application):
    AndroidViewModel(
        application
    ) {
    fun addNavHostController(navHostController: NavHostController
    ) {

        val onDestinationChangedListener=
            object : NavController.OnDestinationChangedListener{
                override fun onDestinationChanged(
                    controller: NavController,
                    destination: NavDestination,
                    arguments: Bundle?
                ) {

                    when(destination.route)
                    {
                        routes[routes.indexOf("OnCreateNav")]->{
                            val isValidUserProfile=
                                mainPrefStorage.getField<UserProfile>(MainPrefStorage.Keys.UserProfile)!!.isValid

                            if (isValidUserProfile)
                                navHostController.navigate(route= routes[routes.indexOf("ScreenMain")]){popUpTo(0)}
                            else
                                navHostController.navigate(route= routes[routes.indexOf("ScreenAuth")]){popUpTo(0)}
                        }
                        routes[routes.indexOf("ScreenCatalog")]->{

                            viewModelScope.launch {

                                try {

                                    mainRepository
                                        .updateMockyContent(mutableMockyContent)

                                }catch (e:Exception){

                                    mainDialog.emit(
                                        errorUpdateMocky(mainDialog)
                                    )

                                }

                            }

                            navHostController.removeOnDestinationChangedListener(this)

                        }
                        else->{}
                    }

                }
            }

        navHostController
            .addOnDestinationChangedListener(onDestinationChangedListener)

    }

    val mainDialog=
        MutableStateFlow<(@Composable ()->Unit)?>(null)

    val mutableMockyContent=
        MutableStateFlow<MockyContent?>(null)

    val mainPrefStorage=
        MainPrefStorage(
            application)

    val mainRepository=
        MainRepository(
            mainPrefStorage,
            application)


}