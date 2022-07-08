package xyz.januzanj.mytestapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import xyz.januzanj.mytestapp.data.MainRepository
import xyz.januzanj.mytestapp.data.response.TodoResponse
import xyz.januzanj.mytestapp.utils.Resource
import java.lang.Exception

class TodoListViewModel (private val mainRepository: MainRepository) : ViewModel() {

    private val todoList: MutableLiveData<MutableList<TodoResponse>> by lazy {
        MutableLiveData<MutableList<TodoResponse>>()
    }

    fun getTodos() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getTodoList()))
        }catch (e: Exception){
            emit(Resource.error(data = null,message = e.message ?: "Error ocurred"))
        }
    }

    fun loadTodos(data:MutableList<TodoResponse>) {
        todoList.value=data
    }

    fun updateVal(position: Int) {
        todoList.value!!.get(position).completed = !todoList.value!!.get(position).completed
    }

    fun getValRow(position: Int): TodoResponse? {
        return todoList.value?.get(position)
    }

}