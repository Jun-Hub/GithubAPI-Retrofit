package io.jun.thingsflow.model

import androidx.lifecycle.LiveData
import androidx.room.*
import io.jun.thingsflow.model.dto.GithubIssue

@Dao
interface GithubDao {
    @Query("SELECT * FROM githubIssue")
    fun getAll(): LiveData<List<GithubIssue>>

    @Query("SELECT * FROM githubIssue WHERE id IN (:issueId)")
    fun loadById(issueId: Int): LiveData<GithubIssue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(githubIssue: GithubIssue)

    @Delete
    fun delete(githubIssue: GithubIssue)

    @Update
    fun update(githubIssue: GithubIssue)
}