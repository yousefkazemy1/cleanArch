package com.example.authdatasource

import android.content.SharedPreferences

class MockSharedPreferences : SharedPreferences {
    private val data: MutableMap<String, Any?> = HashMap()

    override fun getAll(): MutableMap<String, *> = data

    override fun getString(p0: String?, p1: String?): String? = data.getOrDefault(p0, p1) as String

    override fun getStringSet(p0: String?, p1: MutableSet<String>?): MutableSet<String>? =
        data.getOrDefault(p0, p1) as MutableSet<String>

    override fun getInt(p0: String?, p1: Int): Int = data.getOrDefault(p0, p1) as Int

    override fun getLong(p0: String?, p1: Long): Long = data.getOrDefault(p0, p1) as Long

    override fun getFloat(p0: String?, p1: Float): Float = data.getOrDefault(p0, p1) as Float

    override fun getBoolean(p0: String?, p1: Boolean): Boolean =
        data.getOrDefault(p0, p1) as Boolean

    override fun contains(p0: String?): Boolean = data.containsKey(p0)

    override fun edit(): SharedPreferences.Editor = MockEditor()

    override fun registerOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
    }

    override fun unregisterOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
    }

    inner class MockEditor() : SharedPreferences.Editor {
        private val changes: MutableMap<String, Any?> = HashMap()
        override fun putString(p0: String, p1: String?): SharedPreferences.Editor {
            changes.put(p0, p1)
            return this
        }

        override fun putStringSet(p0: String, p1: MutableSet<String>?): SharedPreferences.Editor {
            changes.put(p0, p1)
            return this
        }

        override fun putInt(p0: String, p1: Int): SharedPreferences.Editor {
            changes.put(p0, p1)
            return this
        }

        override fun putLong(p0: String, p1: Long): SharedPreferences.Editor {
            changes.put(p0, p1)
            return this
        }

        override fun putFloat(p0: String, p1: Float): SharedPreferences.Editor {
            changes.put(p0, p1)
            return this
        }

        override fun putBoolean(p0: String, p1: Boolean): SharedPreferences.Editor {
            changes.put(p0, p1)
            return this
        }

        override fun remove(p0: String): SharedPreferences.Editor {
            changes.remove(p0)
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            changes.clear()
            return this
        }

        override fun commit(): Boolean {
            apply();
            return true;
        }

        override fun apply() {
            data.putAll(changes)
            changes.clear()
        }
    }
}