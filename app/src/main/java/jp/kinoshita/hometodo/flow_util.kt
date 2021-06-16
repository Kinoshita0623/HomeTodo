package jp.kinoshita.hometodo

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun FirebaseAuth.listenAuthState() : Flow<FirebaseAuth> {
    return callbackFlow<FirebaseAuth> {
        val listener: (FirebaseAuth)->Unit = {
            trySend(it)
        }
        this@listenAuthState.addAuthStateListener (listener)
        awaitClose {
            this@listenAuthState.removeAuthStateListener(listener)
        }
    }
}