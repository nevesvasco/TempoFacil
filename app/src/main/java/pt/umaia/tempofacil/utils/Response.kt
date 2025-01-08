package pt.umaia.tempofacil.utils

sealed class Response<T> (val data:T?, val message: String? = null) {
    class Loading<T> : Response<T>(null)
    class Success<T>(data: T) : Response<T>(data)

    class Error<T>(message: String): Response<T>(null, message)
}