package io.jun.thingsflow.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GithubIssue(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val org: String,
    @ColumnInfo val repo: String,
    @ColumnInfo val issues: List<Issue>
)

data class Issue (
    val number: Int,
    val title: String,
    val body: String,
    val user: User
)

data class User (
    val login: String,
    val avatar_url: String
)

