package com.halawany.innovationteamtaskpostsapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.halawany.innovationteamtaskpostsapp.core.UiState
import com.halawany.innovationteamtaskpostsapp.core.data.DataState
import com.halawany.innovationteamtaskpostsapp.core.data.ErrorState
import com.halawany.innovationteamtaskpostsapp.core.network.NetworkMonitor
import com.halawany.innovationteamtaskpostsapp.domain.repository.PostRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RefreshPostsUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository = mockk<PostRepository>()
    private val networkMonitor = mockk<NetworkMonitor>()
    private val refreshPostsUseCase = RefreshPostsUseCase(repository, networkMonitor)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refresh posts emits success state theres network and success response from backend`() = runTest {
        // Arrange
        coEvery { repository.refreshPosts() } returns DataState.Success(Unit)
        coEvery { networkMonitor.isConnected() } returns true

        // Act
        val postFromUseCase = refreshPostsUseCase().last()

        // Assert
        assertTrue(postFromUseCase is UiState.Success)
    }

    @Test
    fun `refresh posts emits error state theres no network`() = runTest {
        // Arrange
        coEvery { networkMonitor.isConnected() } returns false

        // Act
        val postFromUseCase = refreshPostsUseCase().last()

        // Assert
        assertTrue(postFromUseCase is UiState.Error && postFromUseCase.message is ErrorState.NoInternet)
    }


    @Test
    fun `refresh posts emits error when network is available but backend fails`() = runTest {
        // Arrange
        coEvery { networkMonitor.isConnected() } returns true
        coEvery { repository.refreshPosts() } returns DataState.Error(ErrorState.Timeout)

        // Act
        val result = refreshPostsUseCase().last()

        // Assert
        assertTrue(result is UiState.Error && result.message is ErrorState.Timeout)
    }

}