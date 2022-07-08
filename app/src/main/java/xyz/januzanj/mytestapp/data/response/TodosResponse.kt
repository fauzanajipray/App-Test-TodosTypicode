package xyz.januzanj.mytestapp.data.response

import com.google.gson.annotations.SerializedName

data class TodoResponse (
    @SerializedName("userId"    ) var userId    : Int?     = null,
    @SerializedName("id"        ) var id        : Int?     = null,
    @SerializedName("title"     ) var title     : String  = "",
    @SerializedName("completed" ) var completed : Boolean = false
)