package com.testeffectivemobile

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.testeffectivemobile.models.MockyContent
import com.testeffectivemobile.ui.errorUpdateMocky
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readBytes
import io.ktor.client.statement.request
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(private val application: Application):AndroidViewModel(application) {

    val mainDialog=
        MutableStateFlow<(@Composable ()->Unit)?>(null)

    fun updateMocky(
    ) {
        viewModelScope.launch {

            try {


                    HttpClient().use {
                        val httpResponse=it.get(application.getString(R.string.url_mocky))
                        val newMockyContent=
                            when(httpResponse.status.value)
                            {
                                200-> MockyContent(String( httpResponse.readBytes()))
                                else->null
                            }

                        if (newMockyContent is JSONObject) {

                            val oldMockyContent=
                                mainRepository.getField<MockyContent>(
                                    MainRepository.Keys.ContentMocky)!!

                            if (!oldMockyContent.isEqualContent(newMockyContent)) {
                                mainRepository.setField(
                                    MainRepository.Keys.ContentMocky,
                                    newMockyContent
                                )
                            }

                        }
                        else
                            throw Exception()

                    }



            }
            catch (e:Exception)
            {
                mainDialog.emit(
                    errorUpdateMocky(mainDialog)
                )
            }

        }
    }

    private val mainRepository=
        MainRepository(application)
}