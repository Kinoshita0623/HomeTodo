package jp.kinoshita.hometodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import jp.kinoshita.hometodo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        db = FirebaseFirestore.getInstance()

        val adapter = TaskListAdapter(this)
        binding.tasksView.adapter = adapter
        binding.tasksView.layoutManager = LinearLayoutManager(this)


        db.collection("tasks").addSnapshotListener { snapshot , e ->


            snapshot?.toObjects(Task::class.java)?.let { tasks ->
                adapter.submitList(tasks)
            }

            
        }
    }

    private fun createTask(task: Task) {
        db.collection("tasks")
            .add(task).addOnSuccessListener {
                Log.d("Firestore", "createTask success")
            }.addOnFailureListener {
                Log.w("Firestore", "createTask failure", it)
            }
    }
}