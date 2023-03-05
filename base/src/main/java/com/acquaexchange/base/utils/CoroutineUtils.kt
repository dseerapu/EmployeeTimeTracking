package com.acquaexchange.base.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

object CoroutineUtils {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun CoroutineScope.safeLaunch(
        exceptionHandler: CoroutineExceptionHandler = coroutineExceptionHandler,
        launchBody: suspend () -> Unit
    ): Job {
        return this.launch(exceptionHandler) {
            launchBody.invoke()
        }
    }

    fun <T> Flow<T>.asStateFlow(
        scope: CoroutineScope,
        defaultValue: T,
        started: SharingStarted = SharingStarted.Eagerly,
    ): StateFlow<T> {
        return stateIn(scope + coroutineExceptionHandler, started, defaultValue)
    }
}

suspend inline fun ReceiveChannel<Unit>.collect(crossinline action: suspend () -> Unit) {
    try {
        val iterator = iterator()
        while (iterator.hasNext()) {
            iterator.next()
            action()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
}

suspend inline fun <T> ReceiveChannel<T>.collect(crossinline action: suspend (value: T) -> Unit) {
    try {
        val iterator = iterator()
        while (iterator.hasNext()) {
            action(iterator.next())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> ConflatedChannel() = Channel<T>(Channel.CONFLATED)

fun <T> Channel<T>.sendValue(value: T) = runCatching { this.trySend(value) }

