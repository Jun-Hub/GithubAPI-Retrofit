package io.jun.thingsflow.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import io.jun.thingsflow.model.dto.Issue
import io.jun.thingsflow.model.dto.User

class Converters {

    @TypeConverter
    fun toJsonIssue(value: List<Issue>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toIssueList(value: String): List<Issue> {
        val objects = Gson().fromJson(value, Array<Issue>::class.java)
        return objects.toList()
    }

    @TypeConverter
    fun toJsonUser(value: User): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toUserList(value: String): User {
        return Gson().fromJson(value, User::class.java)
    }
}