package com.example.zenithrajbhandari_assign1

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
class DataStoreManager (private val context: Context) {
    companion object {
        private val Context.dataUsername: DataStore<Preferences> by preferencesDataStore("Username")
        private val USERNAME = stringPreferencesKey("user_name")

        private val Context.dataEmail: DataStore<Preferences> by preferencesDataStore("Email")
        private val EMAIL = stringPreferencesKey("email")

        private val Context.dataID: DataStore<Preferences> by preferencesDataStore("studentID")
        private val STUDENT_ID = stringPreferencesKey("student_id")
    }

    val getUsername: Flow<String> = context.dataUsername.data.map { preferences ->
        preferences[USERNAME] ?: ""
    }

    val getEmail: Flow<String> = context.dataEmail.data.map { preferences ->
        preferences[EMAIL] ?: ""
    }

    val getStudentID: Flow<String> = context.dataID.data.map { preferences ->
        preferences[STUDENT_ID] ?: ""
    }

    suspend fun saveData (username: String, email: String, studentID: String){
        context.dataUsername.edit { preferences ->
            preferences[USERNAME] = username
        }
        context.dataEmail.edit { preferences ->
            preferences[EMAIL] = email
        }
        context.dataID.edit { preferences ->
            preferences[STUDENT_ID] = studentID
        }
    }


    suspend fun clearData(){
        context.dataUsername.edit { preferences ->
            preferences.remove(USERNAME)
        }
        context.dataEmail.edit { preferences ->
            preferences.remove(EMAIL)
        }
        context.dataID.edit { preferences ->
            preferences.remove(STUDENT_ID)
        }
    }
}