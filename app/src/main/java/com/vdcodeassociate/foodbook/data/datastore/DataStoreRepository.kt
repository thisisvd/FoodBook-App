package com.vdcodeassociate.foodbook.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.vdcodeassociate.foodbook.constants.Constants.Companion.DEFAULT_DIET_TYPE
import com.vdcodeassociate.foodbook.constants.Constants.Companion.DEFAULT_MEAL_TYPE
import com.vdcodeassociate.foodbook.constants.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.vdcodeassociate.foodbook.constants.Constants.Companion.PREFERENCES_DIET_TYPE
import com.vdcodeassociate.foodbook.constants.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.vdcodeassociate.foodbook.constants.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.vdcodeassociate.foodbook.constants.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.vdcodeassociate.foodbook.constants.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

// Data Store repository
@ActivityRetainedScoped // cause this is used inside recipe model
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object PreferencesKeys {
        // meal & diet text and chip id as preferencesKey to deal with them in future
        val selectedMealType = preferencesKey<String>(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeID = preferencesKey<Int>(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(PREFERENCES_DIET_TYPE)
        val selectedDietTypeID = preferencesKey<Int>(PREFERENCES_DIET_TYPE_ID)
        val backOnline = preferencesKey<Boolean>(PREFERENCES_BACK_ONLINE)
    }

    // create datastore instance
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    // save mealType and dietType and their chipID's in datastore
    suspend fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.selectedMealType] = mealType
            preferences[PreferencesKeys.selectedMealTypeID] = mealTypeId
            preferences[PreferencesKeys.selectedDietType] = dietType
            preferences[PreferencesKeys.selectedDietTypeID] = dietTypeId
        }
    }

    // saving back online
    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.backOnline] = backOnline
        }
    }

    // reading back / checking and assigning and getting the data back, if name is empty or to their default or assigned names
    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferencesKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeID = preferences[PreferencesKeys.selectedMealTypeID] ?: 0
            val selectedDietType = preferences[PreferencesKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeID = preferences[PreferencesKeys.selectedDietTypeID] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeID,
                selectedDietType,
                selectedDietTypeID
            )
        }


    // read back online from the preferences datastore
    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferencesKeys.backOnline] ?: false
            backOnline
        }

}

// data class for Meal&DietType
data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)