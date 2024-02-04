package com.testeffectivemobile.models

import com.testeffectivemobile.MainPrefStorage
import org.json.JSONArray
import org.json.JSONObject
import java.util.Collections
import kotlin.reflect.full.isSubclassOf

class MockyCatalog(jsonString: String?=null):
    JSONObject(jsonString?:JSONObject().toString()
    ) {
    fun isEmpty(): Boolean {
        return !keys().hasNext()
    }

    fun isEqualContent(mockyCatalog: MockyCatalog):Boolean {
        return toString()==mockyCatalog.toString()
    }
    var items:JSONArray
        get() =if (has("items")) this@MockyCatalog.getJSONArray("items") else JSONArray()
        set(value) {
            this@MockyCatalog.put("items",value)
        }
    fun item(position:Int):MockyCatalogItem{
        return items.get(position) as MockyCatalogItem
    }

    fun setSorting(mockyCatalogSorting:MockyCatalogSorting){

        var arrayList=
            ArrayList<MockyCatalogItem>()

        val length=items.length()

        for (i in 0 until length)
            arrayList.add(item(i))

        arrayList.sortWith { p0, p1 ->

            when (mockyCatalogSorting) {
                MockyCatalogSorting.Default -> {
                    p0!!.feedbackRating.compareTo(p1!!.feedbackRating)
                }

                MockyCatalogSorting.Decrease -> {
                    p0!!.pricePriceWithDiscount.toDouble().compareTo(p1!!.pricePriceWithDiscount.toDouble())
                }

                MockyCatalogSorting.Increase -> {
                    p1!!.pricePriceWithDiscount.toDouble().compareTo(p0!!.pricePriceWithDiscount.toDouble())
                }
            }
        }

        items= JSONArray(arrayList)

    }

    fun clone(): MockyCatalog {
        return MockyCatalog()
            .apply {
                items=this@MockyCatalog.items
            }
    }

    init {
        for (i in 0 until  items.length())
            items.put(i,MockyCatalogItem(items.getString(i)))
    }
}

sealed class  MockyCatalogSorting{
    companion object {

        @JvmStatic private val map
            get() = MockyCatalogSorting::class.nestedClasses
                .filter { klass -> klass.isSubclassOf(MockyCatalogSorting::class) }
                .map { klass -> klass.objectInstance }
                .filterIsInstance<MockyCatalogSorting>()
                .associateBy { value -> value::class.java.simpleName }

        @JvmStatic fun valueOf(value: String) = requireNotNull(map[value]) {
            "${MockyCatalogSorting::class.java.name}.$value"
        }

        @JvmStatic fun values() = map.values.toTypedArray()
    }
    data object Default:MockyCatalogSorting()
    data object Decrease:MockyCatalogSorting()
    data object Increase:MockyCatalogSorting()
}