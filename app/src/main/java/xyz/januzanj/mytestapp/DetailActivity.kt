package xyz.januzanj.mytestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import xyz.januzanj.mytestapp.data.ApiConfig
import xyz.januzanj.mytestapp.data.RemoteDataSource
import xyz.januzanj.mytestapp.data.response.TodoResponse
import xyz.januzanj.mytestapp.databinding.ActivityDetailBinding
import xyz.januzanj.mytestapp.databinding.ActivityMainBinding
import xyz.januzanj.mytestapp.utils.Status
import xyz.januzanj.mytestapp.viewmodel.TodoListViewModel
import xyz.januzanj.mytestapp.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: TodoListViewModel
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        val item = intent.getParcelableExtra<TodoResponse>("data")
        viewModel = ViewModelProvider(this, ViewModelFactory(RemoteDataSource(ApiConfig.getApiService()))).get(
            TodoListViewModel::class.java
        )

        val todoId: Int = item!!.id!!
        viewModel.getTodoById(todoId).observe(this, Observer {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.pbMain.visibility = View.GONE
                        var data = it.data
                        binding.apply {
                            if (data != null) {
                                tvUser.text = "User " + data.userId
                                tvTitle.text = data.title
                                tvComplete.text = data.completed.toString()
                                if (data.completed) {
                                    checkBox.setImageResource(android.R.drawable.checkbox_on_background)
                                } else {
                                    checkBox.setImageResource(android.R.drawable.checkbox_off_background)
                                }
                            }
                        }
                    }
                    Status.ERROR -> {
                        binding.pbMain.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.pbMain.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}