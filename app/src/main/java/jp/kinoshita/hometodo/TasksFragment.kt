package jp.kinoshita.hometodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import jp.kinoshita.hometodo.databinding.FragmentTasksBinding

class TasksFragment : Fragment(){

    private var _binding: FragmentTasksBinding? = null
    private val binding: FragmentTasksBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val adapter = TaskListAdapter(viewLifecycleOwner)
        binding.tasksView.adapter = adapter
        binding.tasksView.layoutManager = LinearLayoutManager(requireContext())


        db.collection("tasks").addSnapshotListener { snapshot , e ->

            snapshot?.toObjects(Task::class.java)?.let { tasks ->
                adapter.submitList(tasks)
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}