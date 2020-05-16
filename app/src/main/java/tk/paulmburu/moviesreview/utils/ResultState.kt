package tk.paulmburu.moviesreview.utils

sealed class ResultState<out T>

class Success<out T>(val data: T) : ResultState<T>()

class Error<T>(val e: Exception) : ResultState<T>()

class Loading<T> : ResultState<T>()