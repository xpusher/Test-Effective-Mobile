package com.testeffectivemobile

import android.app.Application
import androidx.compose.runtime.Composable
import com.testeffectivemobile.models.MockyCatalog
import com.testeffectivemobile.models.MockyCatalogSorting
import com.testeffectivemobile.ui.errorUpdateMocky
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject

class MainRepository(
    private val mainPrefStorage: MainPrefStorage,
    private val application: Application
) {
    suspend fun requestMockyContent(): MockyCatalog? {

        HttpClient().use {

            val httpResponse=
                it.get(application.getString(R.string.url_mocky))

            val newMockyCatalog=
                when(httpResponse.status.value)
                {
                    200-> MockyCatalog(String( httpResponse.readBytes()))
                    else->null
                }

            return newMockyCatalog

        }

    }

    suspend fun startMockyCatalogSortingJob(
        mutableMockyCatalog: MutableStateFlow<MockyCatalog?>,
        mutableMockyCatalogSorting: MutableStateFlow<MockyCatalogSorting>,
        mainRepository: MainRepository,
        mainDialog: MutableStateFlow<@Composable() (() -> Unit)?>,
    ) {

        suspend fun setSorting(contentMockySorting: MockyCatalogSorting) {

            val newMockyCatalog=
                mainRepository.requestMockyContent()

            if (newMockyCatalog is MockyCatalog) {

                newMockyCatalog
                    .setSorting(contentMockySorting)

                mutableMockyCatalog
                    .emit(newMockyCatalog)

            }
            else {
                mutableMockyCatalog.emit(MockyCatalog())
                throw Exception()
            }

        }

        mutableMockyCatalogSorting.collect{contentMockySorting->

            mainPrefStorage.setField(
                MainPrefStorage.Keys.ContentMockySorting,
                contentMockySorting)

            try {

                setSorting(contentMockySorting)

            }catch (e:Exception){

                mainDialog.emit(
                    errorUpdateMocky(mainDialog)
                )

            }

        }

    }


}
