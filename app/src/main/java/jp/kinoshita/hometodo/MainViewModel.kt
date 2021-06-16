package jp.kinoshita.hometodo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth



class MainViewModel(private val firebaseAuth: FirebaseAuth) : ViewModel(){

    @Suppress("UNCHECKED_CAST")
    class Factory(
        val firebaseAuth: FirebaseAuth
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(firebaseAuth) as T
        }
    }

    private val _authState = MutableLiveData<AuthState>(AuthState.Loading)
    val authState = _authState as LiveData<AuthState>

    init {
        firebaseAuth.addAuthStateListener {
          if(it.currentUser == null) {
              _authState.postValue(AuthState.Unauthenticated)
          }else{
              _authState.postValue(AuthState.Authenticated)
          }
        }

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
}