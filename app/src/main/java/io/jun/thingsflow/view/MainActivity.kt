package io.jun.thingsflow.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.jun.thingsflow.R
import io.jun.thingsflow.adapter.HistoryAdapter
import io.jun.thingsflow.adapter.IssueAdapter
import io.jun.thingsflow.model.dto.GithubIssue
import io.jun.thingsflow.model.dto.Issue
import io.jun.thingsflow.retrofit.RetrofitAPI
import io.jun.thingsflow.retrofit.RetrofitCreator
import io.jun.thingsflow.util.MyUtil
import io.jun.thingsflow.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var githubViewModel: GithubViewModel
    lateinit var historyAdapter: HistoryAdapter

    companion object {
        lateinit var issueAdapter: IssueAdapter
        lateinit var searchTextView: TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        githubViewModel = ViewModelProvider(this).get(GithubViewModel::class.java)

        issueAdapter = IssueAdapter(this)
        historyAdapter = HistoryAdapter(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = issueAdapter
        }

        searchTextView = findViewById(R.id.search_textView)
        searchTextView.setOnClickListener {
            showSearchDialog()
        }

        search_history_textView.setOnClickListener {
            showSearchHistoryDialog()
        }

    }

    private fun searchGithub(org:String, repo:String) {
        val service = RetrofitCreator.createRetrofit().create(RetrofitAPI::class.java)
        service.fetchIssue(org, repo).enqueue(object : Callback<List<Issue>> {
            override fun onFailure(call: Call<List<Issue>>, t: Throwable) {
                Log.e("fail on getIssue", "msg : ${t.message}")
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Issue>>, response: Response<List<Issue>>) {
                // 요청에 성공한 경우
                response.body()?.let { issueList ->
                    if(issueList.isNotEmpty()) {
                        issueAdapter.setIssue(issueList)
                        searchTextView.text = String.format(getString(R.string.searched_issue), org, repo)

                        //Room에 저장
                        CoroutineScope(Dispatchers.Default).launch {
                            githubViewModel.insert(GithubIssue(0, org, repo, issueList))
                        }
                    }
                } ?: Toast.makeText(this@MainActivity, getString(R.string.alert), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showSearchDialog() {
        val inflater = layoutInflater.inflate(R.layout.dialog_search, null as ViewGroup?)

        val orgEditText = inflater.findViewById<EditText>(R.id.org_editText)
        val repoEditText = inflater.findViewById<EditText>(R.id.repo_editText)

        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar)
            .setView(inflater)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val org = orgEditText.text.toString()
                val repo = repoEditText.text.toString()

                searchGithub(org, repo)
                MyUtil.closeKeyboard(this)
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                MyUtil.closeKeyboard(this)
            }
            .show()

        MyUtil.showKeyboard(this)
    }

    private fun showSearchHistoryDialog() {
        val inflater = layoutInflater.inflate(R.layout.dialog_search_history, null as ViewGroup?)

        val recyclerView = inflater.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }

        ViewModelProvider(this).get(GithubViewModel::class.java)
            .allIssues.observe(this, { githubIssues ->
                if(githubIssues.isNotEmpty()) {
                    historyAdapter.setHistory(githubIssues)
                }
            })

        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar)
            .setView(inflater)
            .show()
    }
}