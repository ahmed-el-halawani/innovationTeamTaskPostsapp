package com.halawany.innovationteamtaskpostsapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.halawany.innovationteamtaskpostsapp.core.UiState
import com.halawany.innovationteamtaskpostsapp.core.data.DataState
import com.halawany.innovationteamtaskpostsapp.core.data.ErrorState
import com.halawany.innovationteamtaskpostsapp.domain.model.Post
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

class GetPostsDetailsUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository = mockk<PostRepository>()
    private val post = mockk<Post>()
    private val getPostDetailsUseCase = GetPostDetailsUseCase(repository)

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
    fun `getPost emits success state with PostData `() = runTest {
        // Arrange
        val postId = "uid"
        coEvery { post.id } returns postId
        coEvery { repository.getPost(postId) } returns DataState.Success(post)


        // Act
        val postFromUseCase = getPostDetailsUseCase(post.id).last()

        // Assert
        assertTrue(postFromUseCase is UiState.Success && postFromUseCase.data.id == postId)
    }

    @Test
    fun `getPost emits error state with error NotFound `() = runTest {
        // Arrange
        val postId = "uid"
        coEvery { post.id } returns postId
        coEvery { repository.getPost(postId) } returns DataState.Error(ErrorState.NotFound)

        // Act
        val postFromUseCase = getPostDetailsUseCase(post.id).last()

        // Assert
        assertTrue(postFromUseCase is UiState.Error && postFromUseCase.message == ErrorState.NotFound)
    }


}
