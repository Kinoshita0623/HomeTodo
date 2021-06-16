package jp.kinoshita.hometodo

sealed class AuthState {
    data class  Authenticated(val account: Account) : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
}