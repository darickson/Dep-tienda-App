package com.example.deptienda

import com.example.deptienda.data.repository.AuthRepository
import com.example.deptienda.viewmodel.AuthViewModel
import com.example.deptienda.viewmodel.LoginState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class ExampleUnitTest {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var authRepository: AuthRepository // Este será nuestro "mock"

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        authRepository = mockk()

        authViewModel = AuthViewModel(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when login is successful, loginState is updated to Success`() = runTest(testDispatcher) {
        coEvery { authRepository.login(any(), any()) } returns true

        authViewModel.login("test@example.com", "password123")

        testDispatcher.scheduler.advanceUntilIdle()

        val currentState = authViewModel.loginState.value

        assertTrue("El estado debería ser LoginState.Success", currentState is LoginState.Success)
    }


    @Test
    fun `when login fails, loginState is updated to Error`() = runTest(testDispatcher) {
        coEvery { authRepository.login(any(), any()) } returns false

        authViewModel.login("wrong@example.com", "wrongpassword")

        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = authViewModel.loginState.value

        assertTrue("El estado debería ser LoginState.Error", currentState is LoginState.Error)
    }
}