package jp.kinoshita.hometodo

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SignUpFragment : Fragment(R.layout.fragment_sign_up){

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = ViewModelProvider(this, MainViewModel.Factory(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance()))[MainViewModel::class.java]

        view.findViewById<Button>(R.id.signUpButton).setOnClickListener {
            mainViewModel.signUp()
        }
    }
}