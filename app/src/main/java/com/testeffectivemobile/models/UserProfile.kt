package com.testeffectivemobile.models

import org.json.JSONObject

class UserProfile(stringJSON: String?=null)
    :JSONObject(stringJSON ?: JSONObject().toString()) {

        var firstName:String?
            get() = if (has("firstName"))
                getString("firstName")
            else
                null
            set(value) {
                put("firstName", value)
            }
        var lastName:String?
            get() = if (has("lastName"))
                getString("lastName")
            else
                null
            set(value) {
                put("lastName", value)
            }
        var phone:String?
            get() = if (has("phone"))
                getString("phone")
            else
                null
            set(value) {
                put("phone", value)
            }
    val isValid:Boolean
        get() =
            !phone.isNullOrBlank()
                    &&
                    !firstName.isNullOrBlank()
                    &&
                    !lastName.isNullOrBlank()
}