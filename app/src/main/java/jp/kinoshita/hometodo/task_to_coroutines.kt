package jp.kinoshita.hometodo

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Task<T>.toSuspendable() : T {
    return suspendCoroutine<T> { coroutine ->
        addOnSuccessListener {
            coroutine.resume(it)
        }
        addOnFailureListener {
            coroutine.resumeWithException(it)
        }

    }
}