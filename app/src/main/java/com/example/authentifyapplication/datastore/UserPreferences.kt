package com.example.authentifyapplication.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.authentifyapplication.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferences(private var context: Context) {
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
        val USER_KEY = stringSetPreferencesKey("USER")
        val IS_LOGIN = booleanPreferencesKey("isLogin")
    }

    suspend fun storeUserInfo(user: User) {
        val userAsStringSet =
            setOf(user.name, user.email, user.password, user.birth, user.address, user.gender)
        context.dataStore.edit { preferences ->
            preferences[USER_KEY] = userAsStringSet
            preferences[IS_LOGIN] = false
        }
    }

    suspend fun login() = context.dataStore.edit { preferences -> preferences[IS_LOGIN] = true }
    suspend fun logout() = context.dataStore.edit { preferences -> preferences[IS_LOGIN] = false }

    val userInfo: Flow<User> = context.dataStore.data.map { preferences ->
        val userAsStringSet = preferences[USER_KEY] ?: emptySet()
        val user = if (userAsStringSet.size >= 6) {
            User(
                name = userAsStringSet.elementAt(0),
                email = userAsStringSet.elementAt(1),
                password = userAsStringSet.elementAt(2),
                birth = userAsStringSet.elementAt(3),
                address = userAsStringSet.elementAt(4),
                gender = userAsStringSet.elementAt(5),
            )
        } else {
            User("", "", "", "", "", "")
        }
        user
    }

    val isLogin: Flow<Boolean> =
        context.dataStore.data.map { preferences -> preferences[IS_LOGIN] ?: false }

    suspend fun delete() = context.dataStore.edit { it.clear() }
}