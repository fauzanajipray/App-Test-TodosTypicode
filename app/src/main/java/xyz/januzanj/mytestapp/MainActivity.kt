package xyz.januzanj.mytestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.januzanj.mytestapp.data.ApiConfig
import xyz.januzanj.mytestapp.data.RemoteDataSource
import xyz.januzanj.mytestapp.data.response.TodoResponse
import xyz.januzanj.mytestapp.databinding.ActivityMainBinding
import xyz.januzanj.mytestapp.utils.Status
import xyz.januzanj.mytestapp.viewmodel.TodoListViewModel
import xyz.januzanj.mytestapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoListViewModel
    private lateinit var adapter: TodoListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this, ViewModelFactory(RemoteDataSource(ApiConfig.getApiService()))).get(
                TodoListViewModel::class.java
        )
        viewModel.getTodos().observe(this, Observer {

            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.rvMain.visibility = View.VISIBLE
                        binding.pbMain.visibility = View.GONE
                        it.data?.let { todoList ->
                            updateAdapter(todoList)
                        }
                    }
                    Status.ERROR -> {
                        binding.rvMain.visibility = View.VISIBLE
                        binding.pbMain.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.pbMain.visibility = View.VISIBLE
                        binding.rvMain.visibility = View.GONE
                    }
                }
            }
        })
        binding.rvMain.layoutManager = LinearLayoutManager(this)


        adapter = TodoListAdapter(null, viewModel)
        binding.rvMain.adapter = adapter

        val touchHelperCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    adapter.delete(viewHolder.adapterPosition)
                }

            }
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMain)

    }

    private fun updateAdapter(todoList: List<TodoResponse>) {
        adapter.apply { theTodoList = todoList.toMutableList() }
    }
}