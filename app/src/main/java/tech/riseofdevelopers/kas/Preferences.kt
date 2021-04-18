package tech.riseofdevelopers.kas

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context) {
    companion object {
        const val PREF = "USER_PREF"
//        const val SUM_SALDO = "SUM_SALDO"
//        const val SUM_INCOME = "SUM_INCOME"
//        const val SUM_OUTCOME = "SUM_OUTCOME"
    }

    val sharedPref = context.getSharedPreferences(PREF, 0)

    fun setValues(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setLongValue(key: String,value: Long){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getValues(key: String): String? {
        return sharedPref.getString(key, "")
    }

    fun getLongValues(key: String): Long {
        return sharedPref.getLong(key,0)
    }

    fun createSession() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString("isLogin", "1")
        editor.apply()
    }

    fun logout() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        setValues("isLogin", "0")
        editor.clear()
        editor.apply()
    }

}