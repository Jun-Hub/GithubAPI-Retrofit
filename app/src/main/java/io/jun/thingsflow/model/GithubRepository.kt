package io.jun.thingsflow.model

import androidx.lifecycle.LiveData
import io.jun.thingsflow.model.dto.GithubIssue

class GithubRepository(private val githubDao: GithubDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allIssues: LiveData<List<GithubIssue>> = githubDao.getAll()

    fun insert(githubIssue: GithubIssue) = githubDao.insert(githubIssue)

    fun update(githubIssue: GithubIssue) = githubDao.update(githubIssue)

    fun delete(githubIssue: GithubIssue) = githubDao.delete(githubIssue)

    fun getIssueById(id: Int) = githubDao.loadById(id)
}