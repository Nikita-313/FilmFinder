package com.cinetech.filmfinder.presentation.screens.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinetech.domain.models.CommentsResponse
import com.cinetech.domain.models.LoadCommentsParam
import com.cinetech.domain.usecase.LoadCommentsByMovieIdUseCase
import com.cinetech.domain.usecase.LoadMovieByIdUseCase
import com.cinetech.filmfinder.presentation.helpers.LoadableData
import com.cinetech.filmfinder.presentation.screens.movie.model.MovieScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.cancellation.CancellationException

class MovieViewModel(
    private val id: Int,
    private val loadMovieByIdUseCase: LoadMovieByIdUseCase,
    private val loadCommentsByMovieIdUseCase: LoadCommentsByMovieIdUseCase
) : ViewModel() {

    private val movieScreenState = MutableStateFlow(
        MovieScreenState(
            movie = LoadableData.Loading(),
            comments = LoadableData.Loading()
        )
    )

    fun movieStateFlow(): StateFlow<MovieScreenState> = movieScreenState

    private var loadCommentsParams = LoadCommentsParam(id)
    var loadCommentsDataJob: Job? = null

    init {
        loadMovieData()
        loadCommentsData(loadCommentsParams)
    }

    fun loadMovieData() {
        viewModelScope.launch {
            try {
                movieScreenState.emit(movieScreenState.value.copy(movie = LoadableData.Loading()))

                val movie = loadMovieByIdUseCase.execute(id)

                movieScreenState.emit(movieScreenState.value.copy(movie = LoadableData.Success(movie)))

            } catch (e: Exception) {
                movieScreenState.emit(movieScreenState.value.copy(movie = LoadableData.Error(e.toString())))
                exceptionChecker(e)
            }
        }
    }

    private fun loadCommentsData(loadCommentsParams: LoadCommentsParam, lastCommentsResponse: CommentsResponse? = null) {
        loadCommentsDataJob = viewModelScope.launch {
            try {
                movieScreenState.emit(movieScreenState.value.copy(comments = LoadableData.Loading()))

                var comments = loadCommentsByMovieIdUseCase.execute(loadCommentsParams)

                if (lastCommentsResponse != null) {
                    comments = comments.copy(docs = listOf(*lastCommentsResponse.docs.toTypedArray(), *comments.docs.toTypedArray()))
                }

                movieScreenState.emit(movieScreenState.value.copy(comments = LoadableData.Success(comments)))

            } catch (e: Exception) {
                movieScreenState.emit(movieScreenState.value.copy(comments = LoadableData.Error(e.toString())))
                exceptionChecker(e)
            }
        }
    }

    fun reloadCommentsData(){
        loadCommentsData(loadCommentsParams)
    }

    fun nextCommentPage(lastCommentsResponse: CommentsResponse?) {
        if (lastCommentsResponse == null) return
        if (loadCommentsDataJob?.isActive == true) return
        val nextPage = lastCommentsResponse.page + 1
        if (nextPage > lastCommentsResponse.pages) return
        loadCommentsData(loadCommentsParams.copy(page = nextPage), lastCommentsResponse)
    }


    private fun exceptionChecker(e: Exception) {
        if (e is CancellationException) return
        Log.e("MovieSearchViewModel exceptionChecker", e.toString())
    }


}