package xyz.januzanj.mytestapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.januzanj.mytestapp.data.MainRepository
import xyz.januzanj.mytestapp.data.RemoteDataSource

class ViewModelFactory(val dataSource: RemoteDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
            return TodoListViewModel(MainRepository(dataSource)) as T
        } else {
            throw IllegalArgumentException("Unknown class name")
        }
    }
}