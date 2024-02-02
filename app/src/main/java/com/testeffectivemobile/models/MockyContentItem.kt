package com.testeffectivemobile.models

import org.json.JSONArray
import org.json.JSONObject

class MockyContentItem(stringJSONObject: String?=null):JSONObject(
    if (stringJSONObject==null)
        JSONObject().toString()
    else
        stringJSONObject
) {
    fun isEmpty(): Boolean {
        return !keys().hasNext()
    }

    val id
        get() = getString("id")
    val title
        get() = getString("title")
    val subtitle
        get() = getString("subtitle")

    //region price
    private val price:JSONObject
        get() = getJSONObject("price")
    val pricePrice:Double
        get() = price.getDouble("price")
    val priceDiscount:Double
        get() = price.getDouble("discount")
    val pricePriceWithDiscount:String
        get() = price.getString("priceWithDiscount")
    val priceUnit:String
        get() = price.getString("unit")
    //endregion

    //region feedback
    private val feedback:JSONObject
        get() = getJSONObject("feedback")

    val feedbackCount:Int
        get() = feedback.getInt("count")
    val feedbackRating:Double
        get() = feedback.getDouble("rating")
    //endregion

    val available:Int
        get() = getInt("available")
    val description:String
        get() = getString("description")

    //region info
    private val info:JSONArray
        get() = getJSONArray("info")

    fun infoTitle(position:Int):String {
        return info.getJSONObject(position).getString("title")
    }
    fun infoValue(position: Int):String {
        return info.getJSONObject(position).getString("value")
    }


    //endregion

    val ingredients:String
        get() = getString("ingredients")


}