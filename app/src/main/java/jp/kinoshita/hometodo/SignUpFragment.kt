package jp.kinoshita.hometodo

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment(R.layout.fragment_sign_up){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = ViewModelProvider(this, MainViewModel.Factory(FirebaseAuth.getInstance()))[MainViewModel::class.java]

        view.findViewById<Button>(R.id.signUpButton).setOnClickListener {
            mainViewModel.signUp()
        }
    }
}