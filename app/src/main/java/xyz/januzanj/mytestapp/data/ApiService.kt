package xyz.januzanj.mytestapp.data

import retrofit2.http.GET
import retrofit2.http.Path
import xyz.januzanj.mytestapp.data.response.TodoResponse

interface ApiService {

    @GET("todos")
    suspend fun getTodos() : List<TodoResponse>

    @GET("todos/{todo_id}")
    suspend fun getTodo(
        @Path("todo_id") todo_id : Int
    ) : TodoResponse
}