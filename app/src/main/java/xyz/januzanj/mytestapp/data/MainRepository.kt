package xyz.januzanj.mytestapp.data

import xyz.januzanj.mytestapp.data.RemoteDataSource
import javax.sql.CommonDataSource

class MainRepository(private val dataSource: RemoteDataSource) {
    suspend fun getTodoList() = dataSource.getTodosFromApi()
    suspend fun getTodo() = dataSource.getTodosFromApi()
}