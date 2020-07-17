package com.example.playingwithcoroutineflow.mvvm.models

data class Result<out T>(val status: Status, val data: T?, val error: Throwable?) {

    companion object {
        fun <T> loading(): Result<T> {
            return Result(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable?): Result<T> {
            return Result(Status.ERROR, null, error)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}