package com.gemini.jalen.ligament_commons.util

import android.content.Context
import com.gemini.jalen.ligament.util.AppUtil
import com.gemini.jalen.ligament.util.Storage

fun Context.getVersionCode(): Int {
    return AppUtil.getVersionCode(this)
}

fun Context.getVersionName(): String {
    return AppUtil.getVersionName(this)
}

fun Context.getMetaData(key: String): String {
    return AppUtil.getMetaData(this, key)
}

fun Context.saveString(key: String, value: String): Context {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE).edit()
    storage.putString(key, value).apply()
    return this
}

fun Context.saveBoolean(key: String, value: Boolean): Context {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE).edit()
    storage.putBoolean(key, value).apply()
    return this
}

fun Context.saveFloat(key: String, value: Float): Context {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE).edit()
    storage.putFloat(key, value).apply()
    return this
}

fun Context.saveInt(key: String, value: Int): Context {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE).edit()
    storage.putInt(key, value).apply()
    return this
}

fun Context.saveLong(key: String, value: Long): Context {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE).edit()
    storage.putLong(key, value).apply()
    return this
}

fun Context.saveSet(key: String, value: Set<String>): Context {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE).edit()
    storage.putStringSet(key, value).apply()
    return this
}

fun Context.getString(key: String, defValue: String?=""): String? {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE)
    return storage.getString(key, defValue)
}

fun Context.getBoolean(key: String, defValue: Boolean?=false): Boolean {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE)
    return storage.getBoolean(key, defValue!!)
}

fun Context.getInt(key: String, defValue: Int?=0): Int {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE)
    return storage.getInt(key, defValue!!)
}

fun Context.getFloat(key: String, defValue: Float?=0F): Float {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE)
    return storage.getFloat(key, defValue!!)
}

fun Context.getLong(key: String, defValue: Long?=0L): Long {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE)
    return storage.getLong(key, defValue!!)
}

fun Context.getSet(key: String, defValue: Set<String>?=null): Set<String>? {
    val storage = getSharedPreferences("APP_STORAGE", Context.MODE_PRIVATE)
    return storage.getStringSet(key, defValue)
}