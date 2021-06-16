package jp.kinoshita.hometodo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.kinoshita.hometodo.databinding.ItemTaskBinding

class TaskListAdapter(
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Task, TaskListAdapter.ViewHolder>(TaskDiff){
    class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(task: Task) {
            binding.task = task
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).also {
                it.lifecycleOwner = lifecycleOwner
            }
        )
    }
}

object TaskDiff : DiffUtil.ItemCallback<Task>() {
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.uuid == newItem.uuid
    }
}