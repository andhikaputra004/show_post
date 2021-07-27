package com.example.kumparan.ui.detailuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kumparan.CoroutineTestRule
import com.example.kumparan.data.remote.model.Album
import com.example.kumparan.data.remote.model.Photo
import com.example.kumparan.domain.PostRepository
import com.example.kumparan.utils.UiState
import com.example.kumparan.utils.responseFromJson
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailUserViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    private lateinit var viewModel: DetailUserViewModel

    @Mock
    lateinit var repository: PostRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailUserViewModel(repository, 1)
    }

    @Test
    fun `testGetThumbnail_success_get_data`() = runBlocking {
        val objResponse =
            responseFromJson<Photo>(
                "PhotoResponse.json"
            )
        whenever(repository.getPhotoByAlbumId(1)).thenReturn(objResponse)
        with(viewModel) {
            getThumbnail(listOf(1))
            assertEquals(photos.value, objResponse)
            assertEquals(uiState.value, UiState.Success)
        }
    }

    @Test
    fun `testGetAlbums_success_get_data`() = runBlocking {
        val objResponse =
            responseFromJson<Album>(
                "AlbumResponse.json"
            )
        whenever(repository.getAlbumByUserId(1)).thenReturn(objResponse)
        with(viewModel) {
            getAlbumByUserId(1)
            assertEquals(albums.value, objResponse)
            assertEquals(uiState.value, UiState.Success)
        }
    }
}