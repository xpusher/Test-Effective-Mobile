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
    suspend fun updateMockyContent(mutableMockyCatalog: MutableStateFlow<MockyCatalog?>
    ) {

        val oldMockyCatalog=
            mainPrefStorage.getField<MockyCatalog>(
                MainPrefStorage.Keys.ContentMocky)!!

        if (!oldMockyCatalog.isEmpty())
            mutableMockyCatalog.emit(oldMockyCatalog)

        HttpClient().use {

            val httpResponse=
                it.get(application.getString(R.string.url_mocky))

            val newMockyCatalog=
                when(httpResponse.status.value)
                {
                    200-> MockyCatalog(String( httpResponse.readBytes()))
                    else->null
                }

            if (newMockyCatalog is JSONObject) {

                if (!oldMockyCatalog.isEqualContent(newMockyCatalog)) {

                    mainPrefStorage.setField(
                        MainPrefStorage.Keys.ContentMocky,
                        newMockyCatalog
                    )

                    mutableMockyCatalog.emit(newMockyCatalog)

                }

            }
            else {
                mutableMockyCatalog.emit(MockyCatalog())
                throw Exception()
            }

        }

    }

    suspend fun startMockyCatalogSortingJob(
        mutableMockyCatalog: MutableStateFlow<MockyCatalog?>,
        mutableMockyCatalogSorting: MutableStateFlow<MockyCatalogSorting>,
        mainRepository: MainRepository,
        mainDialog: MutableStateFlow<@Composable() (() -> Unit)?>,
        mainPrefStorage: MainPrefStorage
    ) {

        mutableMockyCatalogSorting.collect{contentMockySorting->


            mainPrefStorage.setField(
                MainPrefStorage.Keys.ContentMockySorting,
                contentMockySorting
            )

            try {

                mainRepository
                    .updateMockyContent(mutableMockyCatalog)

            }catch (e:Exception){

                mainDialog.emit(
                    errorUpdateMocky(mainDialog)
                )

            }

        }

    }


}
