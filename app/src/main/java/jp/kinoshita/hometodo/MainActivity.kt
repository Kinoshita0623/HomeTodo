package jp.kinoshita.hometodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import jp.kinoshita.hometodo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        auth = FirebaseAuth.getInstance()


        val mainViewModel = ViewModelProvider(this, MainViewModel.Factory(auth))[MainViewModel::class.java]

        mainViewModel.authState.observe(this) {
            when(it) {
                is AuthState.Loading -> Log.d("MainActivity", "認証状態読み込み中")
                is AuthState.Authenticated -> {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.baseView, TasksFragment())
                    ft.commit()
                }
                is AuthState.Unauthenticated -> {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.baseView, SignUpFragment())
                    ft.commit()
                }
            }
        }



    }


}