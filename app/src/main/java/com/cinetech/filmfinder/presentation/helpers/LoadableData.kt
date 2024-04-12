package com.cinetech.filmfinder.presentation.helpers


sealed class LoadableData<T> {
    class Success<T>(val value: T) : LoadableData<T>()
    class Error<T>(val massage: String) : LoadableData<T>()
    class Loading<T> : LoadableData<T>()
}