package com.testeffectivemobile.models

import org.json.JSONObject

class MockyContent(jsonString: String?=null):
    JSONObject(jsonString?:JSONObject().toString()) {
    fun isEmpty(): Boolean {
        return !keys().hasNext()
    }

    fun isEqualContent(mockyContent: MockyContent):Boolean {
        return toString()==mockyContent.toString()
    }
}