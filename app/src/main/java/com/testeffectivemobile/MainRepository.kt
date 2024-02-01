package com.testeffectivemobile

import android.app.Application
import android.content.Context
import com.testeffectivemobile.models.MockyContent
import org.json.JSONObject
import kotlin.reflect.full.isSubclassOf

class MainRepository(private val application: Application) :
    JSONObject(application
        .applicationContext
        .getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE)
        .getString(prefName,JSONObject().toString())!!) {

        companion object {
            private val prefName = this::class.java.simpleName
        }
    sealed class  Keys(
        val defaultValue:Any?=null,
        val belongsToPref:Boolean=false,) {
        companion object {

            @JvmStatic private val map
            get() = Keys::class.nestedClasses
                .filter { klass -> klass.isSubclassOf(Keys::class) }
                .map { klass -> klass.objectInstance }
                .filterIsInstance<Keys>()
                .associateBy { value -> value::class.java.simpleName }

            @JvmStatic fun valueOf(value: String) = requireNotNull(map[value]) {
                "${Keys::class.java.name}.$value"
            }

            @JvmStatic fun values() = map.values.toTypedArray()
        }
        data object ContentMocky : Keys(belongsToPref = true, defaultValue = MockyContent())
    }
    @Suppress("UNCHECKED_CAST")
    fun <T>getField(field: Keys):T?
    {
        if (!has(field::class.java.simpleName) && field.defaultValue!=null)
            put(field::class.java.simpleName,field.defaultValue)

        return if (has(field::class.java.simpleName))
            get(field::class.java.simpleName) as T?
        else
            field.defaultValue as T?


    }

    fun setField(field: Keys, any:Any?)
    {

        put(field::class.java.simpleName, any)

        if (field.belongsToPref)
            storeRepository()

    }

    private fun storeRepository(
    ) {

        val jsonObjectForStore=
            JSONObject()

        Keys.values()
            .forEach {
                if (it.belongsToPref && this@MainRepository.has(it::class.java.simpleName))
                    jsonObjectForStore.put(
                        it::class.java.simpleName,
                        this@MainRepository.get(it::class.java.simpleName))
            }

        application
            .applicationContext
            .getSharedPreferences(
                prefName,
                Context.MODE_PRIVATE)
            .edit()
            .putString(
                prefName,
                jsonObjectForStore.toString()
            )
            .apply()

    }

    init {
        if (has(Keys.ContentMocky::class.java.simpleName)) {
                setField(Keys.ContentMocky, MockyContent(getString(Keys.ContentMocky::class.java.simpleName)))
        }
    }
}