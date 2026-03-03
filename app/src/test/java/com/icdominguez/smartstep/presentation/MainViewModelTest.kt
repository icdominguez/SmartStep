@file:OptIn(ExperimentalCoroutinesApi::class)

package com.icdominguez.smartstep.presentation

import com.icdominguez.smartstep.fakes.UserSettingsFake
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var userSettingsFake: UserSettingsFake
    private lateinit var mainViewModel: MainViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userSettingsFake = UserSettingsFake()
        mainViewModel = MainViewModel(userSettingsFake)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `initial state has isLoading true and onBoarding not completed`() {
        assertTrue(mainViewModel.state.isLoading)
        assertFalse(mainViewModel.state.isOnBoardingCompleted)
    }

    @Test
    fun `when gender is set isOnBoardingCompleted is true`() = runTest(testDispatcher) {
        // Arrange
        userSettingsFake.setGender("male")
        advanceUntilIdle()
        
        // Assert
        assertTrue(mainViewModel.state.isOnBoardingCompleted)
        assertFalse(mainViewModel.state.isLoading)
    }
}