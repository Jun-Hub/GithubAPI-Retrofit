package io.jun.thingsflow.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GithubIssue (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val number: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val body: String,
    @ColumnInfo val user: User
)

data class User (
    val login: String,
    val avatar_url: String
)

