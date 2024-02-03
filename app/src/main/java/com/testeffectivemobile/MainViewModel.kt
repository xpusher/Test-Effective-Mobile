package com.testeffectivemobile

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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

        var jobInitNav:Job?=null

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

    val mutableNavController=
        MutableStateFlow<NavHostController?>(null)

    init {
        jobInitNav=
            viewModelScope.launch {
                mutableNavController
                    .collect{navController->

                        when(navController?.currentBackStackEntry?.destination?.route){
                            routes[0]->{
                                navController.navigate(route= routes[1])
                            }
                            routes[2]->{

                                try {

                                    mainRepository
                                        .updateMockyContent(mutableMockyContent)

                                }catch (e:Exception){

                                    mainDialog.emit(
                                        errorUpdateMocky(mainDialog)
                                    )

                                }

                                jobInitNav?.cancel()

                            }
                            else->{}
                        }

                    }
            }
    }
}