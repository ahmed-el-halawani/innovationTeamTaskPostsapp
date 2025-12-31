package com.halawany.innovationteamtaskpostsapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.halawany.innovationteamtaskpostsapp.core.data.DataState
import com.halawany.innovationteamtaskpostsapp.core.data.ErrorState
import com.halawany.innovationteamtaskpostsapp.data.local.PostDao
import com.halawany.innovationteamtaskpostsapp.data.local.PostDatabase
import com.halawany.innovationteamtaskpostsapp.data.local.entity.PostEntity
import com.halawany.innovationteamtaskpostsapp.data.remote.PostApiService
import com.halawany.innovationteamtaskpostsapp.data.remote.dto.NewsResponseDto
import com.halawany.innovationteamtaskpostsapp.data.remote.dto.PostDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

class PostRepositoryTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    private val api = mockk<PostApiService>()
    private val db = mockk<PostDatabase>()
    private val postDao = mockk<PostDao>()
    private val postEntity = mockk<PostEntity>() {
        coEvery { id } returns ""
        coEvery { author } returns ""
        coEvery { description } returns ""
        coEvery { imageUrl } returns ""
        coEvery { link } returns ""
        coEvery { publishedAt } returns ""
        coEvery { source } returns ""
        coEvery { title } returns ""
    }

    private val newsResponseDto = NewsResponseDto("ok", listOf(PostDto("", "", emptyList(), "", "", "", "")))

    private lateinit var repository: PostRepositoryImpl

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { db.dao } returns postDao
        repository = PostRepositoryImpl(api, db)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPost return error not found when post not found in the database `() = runTest {
        // Arrange
        val postId = "uid"
        coEvery { postEntity.id } returns postId


        coEvery { postDao.getPostById(postId) } returns null

        // Act
        val postResult = repository.getPost(postId)


        // Assert
        assertTrue(postResult is DataState.Error)
    }

    @Test
    fun `getPost return success when post found in the database `() = runTest {
        // Arrange
        val postId = "uid"
        coEvery { postEntity.id } returns postId
        coEvery { postDao.getPostById(postId) } returns postEntity

        // Act
        val postResult = repository.getPost(postId)

        // Assert
        assertTrue(postResult is DataState.Success && postResult.data.id == postId)
    }


    @Test
    fun `refresh posts syncing data from network success when theres network and backend return success result`() = runTest {
        // Arrange
        coEvery { postDao.clearPosts() } returns Unit
        coEvery { postDao.insertPosts(any()) } returns Unit
        coEvery { api.getPosts() } returns newsResponseDto

        // Act
        val postResult = repository.refreshPosts()

        // Assert
        assertTrue(postResult is DataState.Success)
    }


    @Test
    fun `refresh posts syncing data from network error when theres network and backend return error result`() = runTest {
        // Arrange
        coEvery { postDao.clearPosts() } returns Unit
        coEvery { postDao.insertPosts(any()) } returns Unit
        coEvery { api.getPosts() } throws SocketTimeoutException()

        // Act
        val postResult = repository.refreshPosts()

        // Assert
        assertTrue(postResult is DataState.Error && postResult.error is ErrorState.Timeout)
    }

}
