@file:OptIn(ExperimentalCoroutinesApi::class)

package com.icdominguez.smartstep.presentation

import com.icdominguez.smartstep.domain.StepCounter
import com.icdominguez.smartstep.domain.UserSettings
import com.icdominguez.smartstep.domain.UserSettingsDefaults
import com.icdominguez.smartstep.fakes.UserSettingsFake
import com.icdominguez.smartstep.presentation.screens.home.HomeAction
import com.icdominguez.smartstep.presentation.screens.home.HomeEvent
import com.icdominguez.smartstep.presentation.screens.home.HomeViewModel
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HomeViewModelTest {
    private val fakeUserSettings = UserSettingsFake()

    private val userSettings: UserSettings = fakeUserSettings
    private val stepCounter: StepCounter = mockk(relaxed = true)

    private lateinit var homeViewModel: HomeViewModel

    private val dispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(dispatcher)

    @BeforeEach
    fun setup() {
        clearMocks(stepCounter, answers = false)
        Dispatchers.setMain(dispatcher)

        // coEvery { userSettings.getBackgroundAccessEnabled() } returns flowOf(null)
        // coEvery { userSettings.getStepGoal() } returns flowOf(10000)
        every { stepCounter.steps } returns MutableStateFlow(0)

        homeViewModel = HomeViewModel(userSettings = userSettings, stepCounter = stepCounter)
        testScope.advanceUntilIdle()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun buildViewModel() {
        homeViewModel = HomeViewModel(
            userSettings = userSettings,
            stepCounter = stepCounter,
        )
    }

    @Test
    fun `init updates state with step goal from settings`() = runTest(dispatcher) {
        // Arrange
        val stepsDefault = UserSettingsDefaults.STEP_DEFAULT
        buildViewModel()
        advanceUntilIdle()

        // Assert
        assertEquals(stepsDefault, homeViewModel.state.value.stepGoal)
        assertEquals(stepsDefault, homeViewModel.state.value.selectedStepGoal)
    }

    @Test
    fun `init updates state with background access enabled from settings`() = runTest(dispatcher) {
        userSettings.setBackgroundAccessEnabled(true)
        advanceUntilIdle()

        assertEquals(true, homeViewModel.state.value.isBackgroundAccessEnabled)
    }

    @Test
    fun `onActivityRecognitionPermissionGranted start stepsCounter and shows background access recommended dialog`() = runTest(dispatcher) {
        // Act
        homeViewModel.onAction(HomeAction.OnActivityRecognitionGranted)
        advanceUntilIdle()

        // Assert
        verify(exactly = 1) { stepCounter.start() }
        assertTrue(homeViewModel.state.value.showBackgroundAccessRecommendedDialog)
    }

    @Test
    fun `onActivityRecognitionDenied shows motion sensors dialog`() {
        // Arrange
        val showShouldShowRationale = false

        // Act
        homeViewModel.onAction(HomeAction.OnActivityRecognitionDenied(showShouldShowRationale))

        // Assert
        assertTrue(homeViewModel.state.value.showEnableAccessManuallyDialog)
    }

    @Test
    fun `onActivityRecognitionDenied shows enable access manually dialog`() {
        // Arrange
        val showShouldShowRationale = true

        // Act
        homeViewModel.onAction(HomeAction.OnActivityRecognitionDenied(showShouldShowRationale))

        // Assert
        assertTrue(homeViewModel.state.value.showMotionSensorsDialog)
    }

    @Test
    fun `onActivityRecognitionRequest shows motion sensors dialog and sends RequestActivityRecognition event`() = runTest(dispatcher) {
        // Act
        homeViewModel.onAction(HomeAction.OnActivityRecognitionRequest)

        // Assert
        assertFalse(homeViewModel.state.value.showMotionSensorsDialog)
        assertEquals(HomeEvent.RequestActivityRecognition, homeViewModel.event.first())
    }

    @Test
    fun `onOpenManualSettings hides enabled access manually dialog and send RequestOpenAppSettings event`() = runTest (dispatcher){
        // Act
        homeViewModel.onAction(HomeAction.OnOpenManualSettings)

        // Assert
        assertFalse(homeViewModel.state.value.showEnableAccessManuallyDialog)
        assertEquals(HomeEvent.RequestOpenAppSettings, homeViewModel.event.first())
    }

    @Test
    fun `onBackgroundAccessPermissionResponse sets background access enabled in user settings`() = runTest(dispatcher) {
        // Arrange
        val isEnabled = false

        // Act
        homeViewModel.onAction(HomeAction.OnBackgroundAccessPermissionResponse(isEnabled))

        // Assert
        val isBackgroundAccessEnabled = fakeUserSettings.getBackgroundAccessEnabled().first()
        assertEquals(false, isBackgroundAccessEnabled)

    }

    @Test
    fun `onActivityRecognitionPermissionChecked starts stepCounter and shows recommend background access dialog`() = runTest {
        // Arrange
        val isActivityRecognitionEnabled = true
        userSettings.setBackgroundAccessEnabled(true)
        advanceUntilIdle()

        // Act
        homeViewModel.onAction(HomeAction.OnActivityRecognitionChecked(isActivityRecognitionEnabled))

        // Assert
        verify(exactly = 1) { stepCounter.start() }
        assertFalse(homeViewModel.state.value.showBackgroundAccessRecommendedDialog)
    }

    @Test
    fun `onActivityRecognitionPermissionChecked starts stepCounter and does not show recommend background access dialog`() {
        // Arrange
        val isActivityRecognitionEnabled = true

        // Act
        homeViewModel.onAction(HomeAction.OnActivityRecognitionChecked(isActivityRecognitionEnabled))

        // Assert
        verify(exactly = 1) { stepCounter.start() }
        assertTrue(homeViewModel.state.value.showBackgroundAccessRecommendedDialog)
    }

    @Test
    fun `onActivityRecognitionPermissionChecked does not start stepCount and sends request activity recognition event`() = runTest(dispatcher){
        // Arrange
        val isActivityRecognitionEnabled = false

        // Act
        homeViewModel.onAction(HomeAction.OnActivityRecognitionChecked(isActivityRecognitionEnabled))

        // Assert
        verify(exactly = 0) { stepCounter.start() }
        assertFalse(homeViewModel.state.value.showBackgroundAccessRecommendedDialog)
        assertEquals(HomeEvent.RequestActivityRecognition, homeViewModel.event.first())
    }

    @Test
    fun `onStepGoalConfirm updates step goal and closes dialog`() = runTest(dispatcher) {
        // Arrange
        val newStepGoal = 12000
        homeViewModel.onAction(HomeAction.OnStepGoalChange(newStepGoal))

        // Act
        homeViewModel.onAction(HomeAction.OnStepGoalConfirm)
        advanceUntilIdle()

        // Assert
        assertEquals(newStepGoal, homeViewModel.state.value.stepGoal)
        assertEquals(newStepGoal, homeViewModel.state.value.selectedStepGoal)

        assertFalse(homeViewModel.state.value.showStepGoalDialog)
    }

    @Test
    fun `onShowStepGoalDialog shows step goal dialog`() {
        // Act
        homeViewModel.onAction(HomeAction.OnShowStepGoalDialog)

        // Assert
        assertTrue(homeViewModel.state.value.showStepGoalDialog)
    }

    @Test
    fun `onContinueBackgroundAccess hides background access dialog and sends RequestBackgroundAccess event`() = runTest(dispatcher) {
        // Act
        homeViewModel.onAction(HomeAction.OnContinueBackgroundAccess)

        // Assert
        assertFalse(homeViewModel.state.value.showBackgroundAccessRecommendedDialog)
        assertEquals(HomeEvent.RequestBackgroundAccess, homeViewModel.event.first())
    }
    
    @Test
    fun `onExitClick shows exit dialog`() {
        // Act
        homeViewModel.onAction(HomeAction.OnExitClick)
        
        // Assert
        assertTrue(homeViewModel.state.value.showExitDialog)
    }
    
    @Test
    fun `onExistDismiss hides exit dialog`() {
        // Act
        homeViewModel.onAction(HomeAction.OnExitDismiss)
        
        // Assert
        assertFalse(homeViewModel.state.value.showExitDialog)
    }

    @Test
    fun `onExitConfirm stops stepCounter hides exit dialog and send request app close event`() = runTest(dispatcher) {
        // Act
        homeViewModel.onAction(HomeAction.OnExitConfirm)

        // Assert
        verify(exactly = 1) { stepCounter.stop() }
        assertFalse(homeViewModel.state.value.showExitDialog)
        assertTrue(HomeEvent.RequestAppClose == homeViewModel.event.first())
    }
}