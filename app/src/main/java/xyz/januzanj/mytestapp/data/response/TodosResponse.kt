package xyz.januzanj.mytestapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoResponse (
    @SerializedName("userId"    ) var userId    : Int?     = null,
    @SerializedName("id"        ) var id        : Int?     = null,
    @SerializedName("title"     ) var title     : String  = "",
    @SerializedName("completed" ) var completed : Boolean = false
) : Parcelable