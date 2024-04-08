package com.cinetech.filmfinder.presentation.screens.movie_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinetech.domain.models.MovieDto
import com.cinetech.domain.models.SearchMovieResponse
import com.cinetech.domain.models.SearchMoviesParam
import com.cinetech.domain.usecase.SearchMoviesByNameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieSearchViewModel(
    private val searchMoviesByNameUseCase: SearchMoviesByNameUseCase
) : ViewModel() {

    private var lastSearchParam: SearchMoviesParam? = null
    private var movies = mutableListOf<MovieDto>()
//    private var data = SearchMovieResponse(
//        docs = emptyList(),
//        total = 0,
//        limit = 0,
//        page = 0,
//        pages = 0,
//    )

    private val searchResultMutableLive = MutableLiveData<List<MovieDto>>(emptyList())
    fun searchResultLive(): LiveData<List<MovieDto>> = searchResultMutableLive

    private val loadingDataIndicatorMutableLive = MutableLiveData(false)
    fun loadingDataIndicatorLive(): LiveData<Boolean> = loadingDataIndicatorMutableLive

    private fun loadMovie(searchMoviesParam: SearchMoviesParam) = viewModelScope.launch(Dispatchers.IO) {
        try {
            loadingDataIndicatorMutableLive.postValue(true)
            val response = searchMoviesByNameUseCase.execute(searchMoviesParam)
            movies.addAll(response.docs)
            lastSearchParam = searchMoviesParam
            searchResultMutableLive.postValue(movies.toList())
        } catch (e: Exception) {
            Log.e("MovieSearchViewModel searchMovie", e.toString())
        } finally {
            loadingDataIndicatorMutableLive.postValue(false)
        }
    }

    fun searchMovie(searchMoviesParam: SearchMoviesParam){
        movies.clear()
        loadMovie(searchMoviesParam)
    }

    fun nextPage(){
        if(loadingDataIndicatorMutableLive.value == true) return
        lastSearchParam?.let {
            loadMovie(it.copy(page = it.page + 1))
        }
    }

    fun f(data:SearchMovieResponse){

    }

}