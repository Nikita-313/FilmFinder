package com.cinetech.filmfinder.presentation.screens.movie_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinetech.domain.models.LoadMoviesResponse
import com.cinetech.domain.models.LoadMoviesParam
import com.cinetech.domain.models.PreviewMovie
import com.cinetech.domain.models.SearchMovieDto
import com.cinetech.domain.models.SearchMoviesParam
import com.cinetech.domain.usecase.LoadMoviesUseCase
import com.cinetech.domain.usecase.SearchMoviesByNameUseCase
import com.cinetech.filmfinder.presentation.screens.movie_search.model.MoviesUiState
import com.cinetech.filmfinder.presentation.screens.movie_search.model.SearchMovieListItem
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieSearchViewModel(
    private val searchMoviesByNameUseCase: SearchMoviesByNameUseCase,
    private val loadMoviesUseCase: LoadMoviesUseCase,
) : ViewModel() {

    private val searchResultMutableLive = MutableLiveData<List<SearchMovieListItem>>(emptyList())
    fun searchResultLive(): LiveData<List<SearchMovieListItem>> = searchResultMutableLive

    private val searchLoadingIndicatorMutableLive = MutableLiveData(false)
    fun searchLoadingIndicatorLive(): LiveData<Boolean> = searchLoadingIndicatorMutableLive

    private val mutableErrorFlow = MutableSharedFlow<String>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    fun errorFlow(): SharedFlow<String> = mutableErrorFlow

    private val moviesMutableStateFlow = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    fun moviesSateFlow(): StateFlow<MoviesUiState> = moviesMutableStateFlow

    private val filterLoadIndicatorMutableStateFlow = MutableStateFlow(false)
    fun filterLoadIndicatorSateFlow(): StateFlow<Boolean> = filterLoadIndicatorMutableStateFlow

    private var lastSearchParam: SearchMoviesParam? = null
    private var searchMovieJob: Job? = null

    private var lastLoadParam = LoadMoviesParam()
    private var lastLoadMoviesResponse: LoadMoviesResponse? = null
    private var loadMovieJob: Job? = null
    private val movies = mutableListOf<PreviewMovie>()

    init {
        loadMovieJob = viewModelScope.launch { loadMovies() }
    }

    private suspend fun loadMovieByName(searchMoviesParam: SearchMoviesParam) {
        try {
            searchLoadingIndicatorMutableLive.postValue(true)
            val response = searchMoviesByNameUseCase.execute(searchMoviesParam)
            lastSearchParam = searchMoviesParam
            searchResultMutableLive.postValue(response.docs.map { it.toSearchMovieItem() })
        } catch (e: Exception) {
            exceptionChecker(e)
        } finally {
            searchLoadingIndicatorMutableLive.postValue(false)
        }
    }

    fun searchMovie(searchMoviesParam: SearchMoviesParam) {

        searchMovieJob?.cancel()
        if (searchMoviesParam.movieName == "") {
            searchResultMutableLive.value = emptyList()
            lastSearchParam = null
            return
        }

        searchMovieJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            loadMovieByName(searchMoviesParam)
        }
    }

    private suspend fun loadMovies() {
        try {
            moviesMutableStateFlow.emit(MoviesUiState.Loading)
            val response = loadMoviesUseCase.execute(lastLoadParam)
            lastLoadMoviesResponse = response
            movies.addAll(response.docs)
            moviesMutableStateFlow.emit(
                MoviesUiState.Success(
                    movies = movies.toList(),
                    pages = response.pages,
                    pageNumber = response.page
                )
            )
        } catch (e: Exception) {
            moviesMutableStateFlow.emit(MoviesUiState.Error(e.toString()))
            exceptionChecker(e)
        }
    }

    fun setNewLoadParam(loadParam: LoadMoviesParam) {
        if (lastLoadParam.countries == loadParam.countries &&
            lastLoadParam.yearRange == loadParam.yearRange &&
            lastLoadParam.ageRantingRange == loadParam.ageRantingRange
        ) return

        filterLoadIndicatorMutableStateFlow.tryEmit(true)
        lastLoadParam = loadParam
        movies.clear()
        loadMovieJob?.cancel()
        loadMovieJob = viewModelScope.launch {
            filterLoadIndicatorMutableStateFlow.tryEmit(true)
            loadMovies()
            filterLoadIndicatorMutableStateFlow.tryEmit(false)
        }

    }
    fun getLastLoadParam() = lastLoadParam

    fun nextMoviesPage() {
        lastLoadMoviesResponse?.let {
            if (loadMovieJob?.isActive == true) return
            val nextPage = it.page + 1
            if (nextPage > it.pages) return
            lastLoadParam = lastLoadParam.copy(page = nextPage)
            loadMovieJob = viewModelScope.launch { loadMovies() }
        }
    }

    fun retryLoadMovies() {
        loadMovieJob?.cancel()
        loadMovieJob = viewModelScope.launch {
            loadMovies()
        }
    }

    private fun exceptionChecker(e: Exception) {
        if (e is CancellationException) return
        mutableErrorFlow.tryEmit(e.toString())
        Log.e("MovieSearchViewModel exceptionChecker", e.toString())
    }

    private fun SearchMovieDto.toSearchMovieItem(): SearchMovieListItem.SearchMovieItem {
        return SearchMovieListItem.SearchMovieItem(
            id = id,
            movieName = name,
            movieAlternativeName = enName ?: "",
            movieYear = year,
            movieRating = kpRating,
            preViewUrl = previewUrl,
        )
    }
}