package com.example.lazypizza.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.lazypizza.core.domain.userdata.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataDataStore(
    private val dataStore: DataStore<Preferences>
) : UserData {
    override fun getCartId(): Flow<String?> {
        return dataStore.data.map {
            it[CART_ID_KEY]
        }
    }

    override fun getIsLoggedIn(): Flow<Boolean> {
        return dataStore.data.map {
            it[USER_ID_KEY] ?: false
        }
    }

    override suspend fun setCardId(cartId: String) {
        dataStore.edit { preferences ->
            preferences[CART_ID_KEY] = cartId
        }
    }

    override suspend fun setIsLoggedIn(loggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = loggedIn
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(CART_ID_KEY)
        }
    }

    companion object {
        private val CART_ID_KEY = stringPreferencesKey("cart_id")
        private val USER_ID_KEY = booleanPreferencesKey("user_id")
    }
}