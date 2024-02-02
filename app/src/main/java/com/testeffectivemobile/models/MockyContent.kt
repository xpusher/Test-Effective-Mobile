package com.testeffectivemobile.models

import org.json.JSONArray
import org.json.JSONObject

class MockyContent(jsonString: String?=null):
    JSONObject(jsonString?:JSONObject().toString()) {
    fun isEmpty(): Boolean {
        return !keys().hasNext()
    }

    fun isEqualContent(mockyContent: MockyContent):Boolean {
        return toString()==mockyContent.toString()
    }
    val items
        get() =if (has("items")) this@MockyContent.getJSONArray("items") else JSONArray()
    fun item(position:Int):MockyContentItem{
        return items.get(position) as MockyContentItem
    }

    init {
        for (i in 0 until  items.length())
            items.put(i,MockyContentItem(items.getString(i)))
    }
}