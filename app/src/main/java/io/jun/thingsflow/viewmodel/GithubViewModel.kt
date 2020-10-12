package io.jun.thingsflow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.jun.thingsflow.model.GithubDatabase
import io.jun.thingsflow.model.dto.GithubIssue
import io.jun.thingsflow.model.GithubRepository

class GithubViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GithubRepository
    val allIssues: LiveData<List<GithubIssue>>

    init {
        val githubDao = GithubDatabase.getDatabase(application).githubDao()
        repository = GithubRepository(githubDao)
        allIssues = repository.allIssues
    }

    fun insert(githubIssue: GithubIssue) = repository.insert(githubIssue)

    fun update(githubIssue: GithubIssue) = repository.update(githubIssue)

    fun delete(githubIssue: GithubIssue) = repository.delete(githubIssue)

    fun getIssueById(id: Int) = repository.getIssueById(id)

    fun getIssue(org:String, repo:String) = repository.getIssue(org, repo)
}