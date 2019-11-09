package ni.devotion.preferenciascompartidas

import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalArgumentException

class AppPreferences(context: Context) {
    private val mPref: SharedPreferences
    private var mEditor: SharedPreferences.Editor? = null
    var SETTINGS_NAME: String = ""
    private var mBulkUpdate = false

    enum class Key{
        miNombrePersistente, miEdadPersistente
    }

    init {
        SETTINGS_NAME = context.packageName
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)
    }

    fun put(key:Key, `val`: Any){
        doEdit()
        when(`val`){
            is String -> mEditor!!.putString(key.name, `val`)
            is Int -> mEditor!!.putInt(key.name, `val`)
            is Double -> mEditor!!.putString(key.name, `val`.toString())
            is Float -> mEditor!!.putFloat(key.name, `val`)
            is Long -> mEditor!!.putLong(key.name, `val`)
        }
        doCommit()
    }

    fun get(key: Key, defaultValue: Any): Any{
        return when(defaultValue){
            is String -> mPref.getString(key.name, defaultValue) as String
            is Int -> mPref.getInt(key.name, defaultValue) as Int
            is Double -> mPref.getString(key.name, defaultValue.toString()).toString().toDouble()
            is Float -> mPref.getFloat(key.name, defaultValue) as Float
            is Long -> mPref.getLong(key.name, defaultValue) as Long
            else -> throw IllegalArgumentException("El valor que deseas obtener no esta permitidio en el AppPreferences")
        }
    }

    fun doCommit(){
        if (!mBulkUpdate && mEditor != null){
            mEditor!!.commit()
            mEditor = null
        }
    }

    fun doEdit(){
        if(!mBulkUpdate && mEditor == null){
            mEditor = mPref.edit()
        }
    }

}