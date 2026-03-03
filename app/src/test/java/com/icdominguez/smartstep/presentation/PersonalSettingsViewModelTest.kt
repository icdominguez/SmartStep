@file:OptIn(ExperimentalCoroutinesApi::class)

package com.icdominguez.smartstep.presentation

import com.icdominguez.smartstep.domain.MeasurementRepository
import com.icdominguez.smartstep.domain.UserSettingsData
import com.icdominguez.smartstep.domain.UserSettingsDefaults
import com.icdominguez.smartstep.domain.model.Gender
import com.icdominguez.smartstep.domain.model.HeightUnit
import com.icdominguez.smartstep.domain.model.WeightUnit
import com.icdominguez.smartstep.fakes.UserSettingsFake
import com.icdominguez.smartstep.presentation.screens.personalsettings.PersonalSettingsAction
import com.icdominguez.smartstep.presentation.screens.personalsettings.PersonalSettingsEvent
import com.icdominguez.smartstep.presentation.screens.personalsettings.PersonalSettingsViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PersonalSettingsViewModelTest {
    private val fakeUserSettings = UserSettingsFake()

    private lateinit var personalSettingsViewModel: PersonalSettingsViewModel
    private val measurementsRepository: MeasurementRepository = mockk(relaxed = true)

    private val dispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(dispatcher)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher = dispatcher)

        personalSettingsViewModel = PersonalSettingsViewModel(
            userSettings = fakeUserSettings,
            measurementRepository = measurementsRepository
        )

        testScope.advanceUntilIdle()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `buildInitialUserSettings with cm and kg sets state without conversions`() = runTest(dispatcher) {
        // Arrange
        val userSettingsData = UserSettingsData(
            gender = UserSettingsDefaults.GENDER_DEFAULT,
            height = UserSettingsDefaults.HEIGHT_DEFAULT,
            weight = UserSettingsDefaults.WEIGHT_DEFAULT,
            selectedHeightUnit = UserSettingsDefaults.HEIGHT_UNIT_DEFAULT,
            selectedWeightUnit = UserSettingsDefaults.WEIGHT_UNIT_DEFAULT
        )

        // Act
        personalSettingsViewModel.buildInitialUserSettings(userSettingsData)

        // Assert
        verify(exactly = 0) { measurementsRepository.cmToFeetAndInches(any<Int>()) }
        verify(exactly = 0) { measurementsRepository.kgToLbs(any<Int>()) }

        val state = personalSettingsViewModel.state.value
        assertEquals(Gender.FEMALE, state.gender)

        assertEquals(HeightUnit.CENTIMETERS, state.heightUnit)
        assertEquals(HeightUnit.CENTIMETERS, state.selectedHeightUnit)
        assertEquals(userSettingsData.height, state.height)
        assertEquals(userSettingsData.height, state.selectedHeightValue)
        assertEquals(0, state.selectedHeightFeet)
        assertEquals(0, state.selectedHeightInches)
        assertEquals("${userSettingsData.height} ${HeightUnit.CENTIMETERS.label}", state.displayHeight)

        assertEquals(WeightUnit.KILOS, state.weightUnit)
        assertEquals(WeightUnit.KILOS, state.selectedWeightUnit)
        assertEquals(userSettingsData.weight, state.weight)
        assertEquals(userSettingsData.weight, state.selectedWeightValue)
        assertEquals("${userSettingsData.weight} ${WeightUnit.KILOS.label}", state.displayWeight)

    }

    @Test
    fun `buildInitialUserSettings with feet and inches and lbs sets state with conversions`() = runTest(dispatcher) {
        // Arrange
        val userSettingsData = UserSettingsData(
            gender = Gender.MALE.name,
            height = UserSettingsDefaults.HEIGHT_DEFAULT,
            weight = UserSettingsDefaults.WEIGHT_DEFAULT,
            selectedHeightUnit = HeightUnit.FEET.label,
            selectedWeightUnit = WeightUnit.POUNDS.label,
        )

        val feetAndInches = Pair(6, 1)
        val lbs = 183

        every { measurementsRepository.cmToFeetAndInches(any<Int>()) } returns feetAndInches
        every { measurementsRepository.kgToLbs(any<Int>()) } returns lbs

        // Act
        personalSettingsViewModel.buildInitialUserSettings(userSettingsData)

        // Assert
        verify(exactly = 1) { measurementsRepository.cmToFeetAndInches(any<Int>()) }
        verify(exactly = 1) { measurementsRepository.kgToLbs(any<Int>()) }

        val state = personalSettingsViewModel.state.value
        assertEquals(Gender.MALE, state.gender)

        assertEquals(HeightUnit.FEET, state.heightUnit)
        assertEquals(HeightUnit.FEET, state.selectedHeightUnit)
        assertEquals(userSettingsData.height, state.height)
        assertEquals(userSettingsData.height, state.selectedHeightValue)
        assertEquals(feetAndInches.first, state.selectedHeightFeet)
        assertEquals(feetAndInches.second, state.selectedHeightInches)
        assertEquals("6ft/1in", state.displayHeight)

        assertEquals(WeightUnit.POUNDS, state.weightUnit)
        assertEquals(WeightUnit.POUNDS, state.selectedWeightUnit)
        assertEquals(lbs, state.weight)
        assertEquals(lbs, state.selectedWeightValue)
        assertEquals("$lbs ${WeightUnit.POUNDS.label}", state.displayWeight)

    }

    @Test
    fun `setGender updates gender state`() = runTest(dispatcher) {
        // Arrange
        val gender = Gender.MALE

        // Act
        personalSettingsViewModel.onAction(PersonalSettingsAction.SetGender(gender))
        advanceUntilIdle()

        // Assert
        assertEquals(Gender.MALE, personalSettingsViewModel.state.value.gender)
    }

    @Test
    fun `setWeightUnit to Kilos updates weightUnit and converts value`() {
        // Arrange
        val lbsToKg = 70
        val selectedWeightUnit = WeightUnit.KILOS
        every { measurementsRepository.lbsToKg(any()) } returns lbsToKg

        // Act
        personalSettingsViewModel.onAction(PersonalSettingsAction.SetWeightUnit(selectedWeightUnit))

        // Assert
        verify(exactly = 1) { measurementsRepository.lbsToKg(any()) }
        assertEquals(selectedWeightUnit, personalSettingsViewModel.state.value.selectedWeightUnit)
        assertEquals(lbsToKg, personalSettingsViewModel.state.value.selectedWeightValue)
    }
    
    @Test
    fun `setWeightUnit to Pounds updates weight and converts value`() {
        // Arrange
        val kgToLbs = 200
        val selectedWeightUnit = WeightUnit.POUNDS
        every { measurementsRepository.kgToLbs(any()) } returns kgToLbs

        // Act
        personalSettingsViewModel.onAction(PersonalSettingsAction.SetWeightUnit(selectedWeightUnit))
        
        // Assert
        verify(exactly = 1) { measurementsRepository.kgToLbs(any()) }
        assertEquals(selectedWeightUnit, personalSettingsViewModel.state.value.selectedWeightUnit)
        assertEquals(kgToLbs, personalSettingsViewModel.state.value.selectedWeightValue)
    }

    @Test
    fun `setHeightUnit to centimeters updates height and converts values`() {
        // Arrange
        val selectedHeightUnit = HeightUnit.CENTIMETERS
        val centimeters = 180
        every { measurementsRepository.feetAndInchesToCm(any(), any()) } returns centimeters

        // Act
        personalSettingsViewModel.onAction(PersonalSettingsAction.SetHeightUnit(selectedHeightUnit))

        // Assert
        verify(exactly = 1) { measurementsRepository.feetAndInchesToCm(any(), any()) }
        assertEquals(selectedHeightUnit, personalSettingsViewModel.state.value.selectedHeightUnit)
        assertEquals(selectedHeightUnit, personalSettingsViewModel.state.value.selectedHeightUnit)
    }

    @Test
    fun `setHeightUnit to feet and inches updates height and converts values`() {
        // Arrange
        val selectedHeightUnit = HeightUnit.FEET
        val feetAndInches = Pair(5, 10)
        every { measurementsRepository.cmToFeetAndInches(any<Int>()) } returns feetAndInches

        // Act
        personalSettingsViewModel.onAction(PersonalSettingsAction.SetHeightUnit(selectedHeightUnit))

        // Assert
        verify(exactly = 1) { measurementsRepository.cmToFeetAndInches(any<Int>()) }
        assertEquals(selectedHeightUnit, personalSettingsViewModel.state.value.selectedHeightUnit)
        assertEquals(feetAndInches.first, personalSettingsViewModel.state.value.selectedHeightFeet)
        assertEquals(feetAndInches.second, personalSettingsViewModel.state.value.selectedHeightInches)

    }

    @Test
    fun `onSetNewWeightValue updates its value`() {
        // Arrange
        val weight = 175

        // Act
        personalSettingsViewModel.onAction(PersonalSettingsAction.OnWeightValueChange(weight))

        // Assert
        assertEquals(weight, personalSettingsViewModel.state.value.selectedWeightValue)
    }

    @Test
    fun `onDismissHeightPicker converts default value and updates state`() {
        // Arrange
        val feetAndInches = Pair(5, 10)
        val selectedHeightUnit = HeightUnit.FEET
        every { measurementsRepository.cmToFeetAndInches(any()) } returns feetAndInches

        // Act
        personalSettingsViewModel.onAction(PersonalSettingsAction.SetHeightUnit(selectedHeightUnit))
        personalSettingsViewModel.onAction(PersonalSettingsAction.OnDismissHeightPicker)

        // Assert
        verify { measurementsRepository.cmToFeetAndInches(any<Int>()) }
        assertEquals(personalSettingsViewModel.state.value.selectedHeightUnit, HeightUnit.CENTIMETERS)
        assertEquals(personalSettingsViewModel.state.value.height, personalSettingsViewModel.state.value.selectedHeightValue)
        assertEquals(feetAndInches.first, personalSettingsViewModel.state.value.selectedHeightFeet)
        assertEquals(feetAndInches.second, personalSettingsViewModel.state.value.selectedHeightInches)
    }

    @Test
    fun `onSavePersonalSettingsClick converts feet and pounds, saves settings, and sends event`() = runTest(dispatcher) {
        // Assert
        val gender = Gender.MALE
        val height = 185
        val weight = 83
        val feetAndInches = Pair(6, 1)
        val heightUnit = HeightUnit.FEET
        val weightUnit = WeightUnit.POUNDS

        every { measurementsRepository.cmToFeetAndInches(any<Int>()) } returns feetAndInches
        every { measurementsRepository.feetAndInchesToCm(any<Int>(), any<Int>()) } returns height
        every { measurementsRepository.lbsToKg(any<Int>()) } returns weight

        personalSettingsViewModel.onAction(PersonalSettingsAction.SetGender(gender))

        personalSettingsViewModel.onAction(PersonalSettingsAction.SetHeightUnit(heightUnit))
        personalSettingsViewModel.onAction(PersonalSettingsAction.OnHeightValueChange(height))
        personalSettingsViewModel.onAction(PersonalSettingsAction.OnSetNewHeightValue)

        personalSettingsViewModel.onAction(PersonalSettingsAction.SetWeightUnit(weightUnit))
        personalSettingsViewModel.onAction(PersonalSettingsAction.OnWeightValueChange(weight))
        personalSettingsViewModel.onAction(PersonalSettingsAction.OnSetNewWeightValue)

        // Act
        personalSettingsViewModel.onAction(PersonalSettingsAction.OnStartButtonClicked)
        advanceUntilIdle()

        // Assert
        verify(exactly = 1) { measurementsRepository.cmToFeetAndInches(any<Int>()) }
        verify(exactly = 1) { measurementsRepository.feetAndInchesToCm(any<Int>(), any<Int>()) }
        verify(exactly = 1) { measurementsRepository.lbsToKg(any<Int>()) }

        val userSettings = fakeUserSettings.getUserSettingsData().first()
        assertEquals(gender.name, userSettings.gender)
        assertEquals(height, userSettings.height)
        assertEquals(heightUnit.label, userSettings.selectedHeightUnit)
        assertEquals(weight, userSettings.weight)
        assertEquals(weightUnit.label, userSettings.selectedWeightUnit)

        assertEquals(PersonalSettingsEvent.OnPersonalSettingsSaved,personalSettingsViewModel.event.first())
    }
}