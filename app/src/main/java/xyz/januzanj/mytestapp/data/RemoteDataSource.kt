package xyz.januzanj.mytestapp.data

class RemoteDataSource(val apiService: ApiService ) {
    suspend fun getTodosFromApi() = apiService.getTodos()
    suspend fun getTodoFromApi(todo_id: Int) = apiService.getTodo(todo_id)
}