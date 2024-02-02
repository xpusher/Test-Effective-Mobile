package com.testeffectivemobile

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.testeffectivemobile.models.MockyContent
import com.testeffectivemobile.ui.MainAppNavState
import com.testeffectivemobile.ui.errorUpdateMocky
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application):
    AndroidViewModel(
        application
    ) {

    val mainDialog=
        MutableStateFlow<(@Composable ()->Unit)?>(null)

    val mutableMockyContent=
        MutableStateFlow<MockyContent?>(null)


    private val mainPrefStorage=
        MainPrefStorage(application)

    val mainRepository=
        MainRepository(
            mainPrefStorage,
            application)

    val mutableNavRouteState=
        MutableStateFlow<MainAppNavState?>(null)

    init {
        viewModelScope.launch {
            mutableNavRouteState.collect{currentRoute->

                when(currentRoute){
                    MainAppNavState.ScreenAuth->{

                    }
                    MainAppNavState.CreateNav->{

                        mutableNavRouteState
                            .emit(MainAppNavState.ScreenAuth)
                    }
                    MainAppNavState.ScreenCatalog->{

                        try {

                            mainRepository
                                .updateMockyContent(mutableMockyContent)

                        }catch (e:Exception){
                            mainDialog.emit(
                                errorUpdateMocky(mainDialog)
                            )
                        }

                    }
                    else->{}
                }

            }
        }
    }
}