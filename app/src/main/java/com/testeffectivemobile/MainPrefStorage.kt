package com.testeffectivemobile

import android.content.Context
import com.testeffectivemobile.models.MockyCatalog
import com.testeffectivemobile.models.MockyCatalogSorting
import com.testeffectivemobile.models.UserProfile
import org.json.JSONObject
import kotlin.reflect.full.isSubclassOf

class MainPrefStorage(private val context: Context) :
    JSONObject(context
        .applicationContext
        .getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE)
        .getString(prefName,JSONObject().toString())!!
    ) {

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
        data object ContentMocky : Keys(belongsToPref = true, defaultValue = MockyCatalog())
        data object ContentMockySorting : Keys(belongsToPref = true, defaultValue = MockyCatalogSorting.Default)
        data object UserProfile : Keys(belongsToPref = true, defaultValue = UserProfile())
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
                if (it.belongsToPref && this@MainPrefStorage.has(it::class.java.simpleName))
                    jsonObjectForStore.put(
                        it::class.java.simpleName,
                        this@MainPrefStorage.get(it::class.java.simpleName))
            }

        context
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
        if (has(Keys.ContentMocky::class.java.simpleName))
        {
            setField(Keys.ContentMocky, MockyCatalog(getString(Keys.ContentMocky::class.java.simpleName)))
        }
        if (has(Keys.UserProfile::class.java.simpleName))
        {
            setField(Keys.UserProfile, UserProfile(getString(Keys.UserProfile::class.java.simpleName)))
        }
        if (has(Keys.ContentMockySorting::class.java.simpleName)) {
            setField(
                Keys.ContentMockySorting,
                MockyCatalogSorting.valueOf(getString(Keys.ContentMockySorting::class.java.simpleName)))
        }
    }
}