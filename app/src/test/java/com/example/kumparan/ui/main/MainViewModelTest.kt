package com.example.kumparan.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kumparan.CoroutineTestRule
import com.example.kumparan.data.remote.model.Post
import com.example.kumparan.data.remote.model.User
import com.example.kumparan.domain.PostRepository
import com.example.kumparan.utils.UiState
import com.example.kumparan.utils.responseFromJson
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var repository: PostRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `testGetPost_success_get_data`() = runBlocking {
        val objResponse =
            responseFromJson<Post>(
                "PostResponse.json"
            )
        whenever(repository.getAllPostUsers()).thenReturn(objResponse)
        with(viewModel) {
            getPost()
            assertEquals(post.value, objResponse)
            assertEquals(uiState.value, UiState.Success)
        }
    }

    @Test
    fun `testGetUsers_success_get_data`() = runBlocking {
        val objResponse =
            responseFromJson<User>(
                "UsersResponse.json"
            )
        whenever(repository.getAllUsers()).thenReturn(objResponse)
        with(viewModel) {
            getUsers()
            assertEquals(users.value, objResponse)
            assertEquals(uiState.value, UiState.Success)
        }
    }
}