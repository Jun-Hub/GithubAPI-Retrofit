package io.jun.thingsflow.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.jun.thingsflow.R
import io.jun.thingsflow.model.dto.GithubIssue
import io.jun.thingsflow.view.MainActivity

class HistoryAdapter internal constructor(private val context: Context)
    : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var items: List<GithubIssue> = emptyList()

    inner class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val historyTextView: TextView = itemView.findViewById(R.id.history_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): HistoryViewHolder {
        val itemView = inflater.inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holer: HistoryViewHolder, position: Int) {
        items[position].let { item ->
            with(holer) {

                historyTextView.text = String.format(context.getString(R.string.searched_issue), item.org, item.repo)

                historyTextView.setOnClickListener {
                    MainActivity.issueAdapter.setIssue(item.issues)
                    MainActivity.searchTextView.text = String.format(context.getString(R.string.searched_issue), item.org, item.repo)
                }
            }
        }
    }

    fun setHistory(githubIssues: List<GithubIssue>) {
        items = githubIssues
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}

