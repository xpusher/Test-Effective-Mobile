package com.testeffectivemobile

import android.app.Application
import com.testeffectivemobile.models.MockyContent
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject

class MainRepository(
    private val mainPrefStorage: MainPrefStorage,
    private val application: Application) {
    suspend fun updateMockyContent(mutableMockyContent: MutableStateFlow<MockyContent?>
    ) {

        val oldMockyContent=
            mainPrefStorage.getField<MockyContent>(
                MainPrefStorage.Keys.ContentMocky)!!

        if (!oldMockyContent.isEmpty())
            mutableMockyContent.emit(oldMockyContent)

        HttpClient().use {

            val httpResponse=
                it.get(application.getString(R.string.url_mocky))

            val newMockyContent=
                when(httpResponse.status.value)
                {
                    200-> MockyContent(String( httpResponse.readBytes()))
                    else->null
                }

            if (newMockyContent is JSONObject) {

                if (!oldMockyContent.isEqualContent(newMockyContent)) {

                    mainPrefStorage.setField(
                        MainPrefStorage.Keys.ContentMocky,
                        newMockyContent
                    )

                    if (!oldMockyContent.isEmpty())
                        mutableMockyContent.emit(newMockyContent)

                }

            }
            else {
                mutableMockyContent.emit(MockyContent())
                throw Exception()
            }

        }

    }

}