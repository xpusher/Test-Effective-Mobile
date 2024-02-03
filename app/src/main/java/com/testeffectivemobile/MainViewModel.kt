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
import com.testeffectivemobile.ui.errorUpdateMocky
import com.testeffectivemobile.ui.routes
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application):
    AndroidViewModel(
        application
    ) {
    fun addNavHostController(navHostController: NavHostController) {

        val onDestinationChangedListener=
            object : NavController.OnDestinationChangedListener{
                override fun onDestinationChanged(
                    controller: NavController,
                    destination: NavDestination,
                    arguments: Bundle?
                ) {

                    when(destination.route)
                    {
                        routes[0]->{
                            navHostController.navigate(route= routes[1])
                        }
                        routes[2]->{

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
        MainPrefStorage(application)

    val mainRepository=
        MainRepository(
            mainPrefStorage,
            application)


}