package jp.kinoshita.hometodo

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import java.util.*


@ExperimentalCoroutinesApi
class MainViewModel(private val firebaseAuth: FirebaseAuth, private val fireStore: FirebaseFirestore) : ViewModel(){

    @Suppress("UNCHECKED_CAST")
    class Factory(
        val firebaseAuth: FirebaseAuth,
        val fireStore: FirebaseFirestore,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(firebaseAuth, fireStore) as T
        }
    }

    private val _authState = MutableLiveData<AuthState>(AuthState.Loading)
    val authState = _authState as LiveData<AuthState>

    init {

        firebaseAuth.listenAuthState().onEach {
            if(it.currentUser == null) {
                _authState.postValue(AuthState.Unauthenticated)
            }else{
                val ac = getAccountIfExistsCreate(it.currentUser!!)
                _authState.postValue(AuthState.Authenticated(ac))
            }
        }.catch { e ->
            Log.w("MainVM", "listenAuthState error", e)
        }.launchIn( viewModelScope + Dispatchers.IO)

    }

    fun signUp() {
        firebaseAuth.signInAnonymously().addOnSuccessListener {
            if(it.user != null) {
                _authState.postValue(AuthState.Unauthenticated)
            }
        }.addOnFailureListener {
            Log.w("MainVM", "singIn fail", it)
        }
    }

    private suspend fun getAccountIfExistsCreate(user: FirebaseUser) : Account{
        val u = fireStore.collection("accounts")
            .document(user.uid)
            .get()
            .toSuspendable()
        if(u.exists()) {
            return u.toObject(Account::class.java)!!
        }

        fireStore.collection("accounts")
            .document(user.uid)
            .set(mapOf("uid" to user.uid, "uuid" to UUID.randomUUID().toString()))
            .toSuspendable()
        return fireStore.collection("accounts")
            .document(user.uid)
            .get()
            .toSuspendable()
            .toObject(Account::class.java)!!

    }
}