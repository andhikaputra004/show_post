package com.example.kumparan.ui.detailpost

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kumparan.CoroutineTestRule
import com.example.kumparan.data.remote.model.Comment
import com.example.kumparan.data.remote.model.PostDTO
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

class DetailPostViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    private lateinit var viewModel: DetailPostViewModel

    @Mock
    lateinit var repository: PostRepository

    @Mock
    lateinit var selectedPostDTO: PostDTO

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailPostViewModel(repository, selectedPostDTO)
    }

    @Test
    fun `getCommentByPostId_success_get_data`() = runBlocking {
        val objResponse =
            responseFromJson<Comment>(
                "CommentResponse.json"
            )
        whenever(repository.getCommentByPostId(1)).thenReturn(objResponse)
        with(viewModel) {
            getCommentByPostId(1)
            assertEquals(comment.value, objResponse)
            assertEquals(uiState.value, UiState.Success)
        }
    }
}