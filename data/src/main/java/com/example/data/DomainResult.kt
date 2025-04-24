package com.example.data

sealed class DomainResult<E> {
    data class Success<E>(val data: E): DomainResult<E>()
    data class Error<E>(val error: Throwable): DomainResult<E>()
}