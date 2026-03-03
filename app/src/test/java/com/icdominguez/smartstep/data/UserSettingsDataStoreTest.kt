@file:OptIn(ExperimentalCoroutinesApi::class)

package com.icdominguez.smartstep.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.icdominguez.smartstep.domain.UserSettingsDefaults
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.io.TempDir
import java.io.File

class UserSettingsDataStoreTest {
    @TempDir
    lateinit var tempDir: File

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var userSettingsDataStore: UserSettingsDataStore

    @BeforeEach
    fun setUp() {
        dataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { File(tempDir, "test_user_settings.preferences_pb") }
        )
        userSettingsDataStore = UserSettingsDataStore(dataStore)
    }

    @AfterEach
    fun tearDown() {
        File(tempDir, "test_user_settings.preferences_pb").delete()
    }

    @Test
    fun `getUserSettingsData returns default values when no data is set`() = testScope.runTest {
        val result = userSettingsDataStore.getUserSettingsData().first()

        assertEquals(UserSettingsDefaults.GENDER_DEFAULT, result.gender)
        assertEquals(UserSettingsDefaults.HEIGHT_DEFAULT, result.height)
        assertEquals(UserSettingsDefaults.WEIGHT_DEFAULT, result.weight)
        assertEquals(UserSettingsDefaults.HEIGHT_UNIT_DEFAULT, result.selectedHeightUnit)
        assertEquals(UserSettingsDefaults.WEIGHT_UNIT_DEFAULT, result.selectedWeightUnit)
    }

    @Test
    fun `setGender updates gender correctly`() = testScope.runTest {
        userSettingsDataStore.setGender("male")
        val result = userSettingsDataStore.getUserSettingsData().first()
        assertEquals("male", result.gender)
    }

    @Test
    fun `setHeight updates height correctly`() = testScope.runTest {
        userSettingsDataStore.setHeight(180)
        val result = userSettingsDataStore.getUserSettingsData().first()
        assertEquals(180, result.height)
    }

    @Test
    fun `setWeight updates weight correctly`() = testScope.runTest {
        userSettingsDataStore.setWeight(75)
        val result = userSettingsDataStore.getUserSettingsData().first()
        assertEquals(75, result.weight)
    }

    @Test
    fun `setHeightUnit updates height unit correctly`() = testScope.runTest {
        userSettingsDataStore.setHeightUnit("ft")
        val result = userSettingsDataStore.getUserSettingsData().first()
        assertEquals("ft", result.selectedHeightUnit)
    }

    @Test
    fun `setWeightUnit updates weight unit correctly`() = testScope.runTest {
        userSettingsDataStore.setWeightUnit("lbs")
        val result = userSettingsDataStore.getUserSettingsData().first()
        assertEquals("lbs", result.selectedWeightUnit)
    }

    @Test
    fun `getBackgroundAccessEnabled returns null when not set`() = testScope.runTest {
        val result = userSettingsDataStore.getBackgroundAccessEnabled().first()
        assertNull(result)
    }

    @Test
    fun `setBackgroundAccessEnabled updates value correctly`() = testScope.runTest {
        userSettingsDataStore.setBackgroundAccessEnabled(true)
        val result = userSettingsDataStore.getBackgroundAccessEnabled().first()
        assertEquals(true, result)
    }

    @Test
    fun `getStepGoal returns default 10000 when not set`() = testScope.runTest {
        val result = userSettingsDataStore.getStepGoal().first()
        assertEquals(10000, result)
    }

    @Test
    fun `setStepGoal updates step goal correctly`() = testScope.runTest {
        userSettingsDataStore.setStepGoal(8000)
        val result = userSettingsDataStore.getStepGoal().first()
        assertEquals(8000, result)
    }
}