package xyz.januzanj.mytestapp

import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import xyz.januzanj.mytestapp.data.response.TodoResponse
import xyz.januzanj.mytestapp.viewmodel.TodoListViewModel

class TodoListAdapter(
    var theTodoList: MutableList<TodoResponse>?,
    val viewModel: TodoListViewModel
) : RecyclerView.Adapter<TodoListAdapter.TodosViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class TodosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val checkBox: ImageView = itemView.findViewById(R.id.checkBox)

        init {
            checkBox.setOnClickListener {
                theTodoList?.get(adapterPosition)?.completed =
                    !theTodoList?.get(adapterPosition)?.completed!!
                notifyItemChanged(adapterPosition)
                viewModel.loadTodos(theTodoList!!)
            }
        }

        fun bind(data: TodoResponse) {
            tvTitle.text = data.title
            if (data.completed) {
                checkBox.setImageResource(android.R.drawable.checkbox_on_background)
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        androidx.appcompat.R.color.material_deep_teal_200
                    )
                )
                tvTitle.alpha = 1f
            } else {
                checkBox.setImageResource(android.R.drawable.checkbox_off_background)
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        var root = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return TodosViewHolder(root)
    }

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        holder.bind(theTodoList!![position])
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(theTodoList!![position])
        }
    }

    override fun getItemCount(): Int {
        return theTodoList?.size ?: 0
    }

    fun delete(adapterPosition: Int) {
        theTodoList!!.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
        viewModel.loadTodos(theTodoList!!)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TodoResponse)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}