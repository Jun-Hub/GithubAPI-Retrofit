package io.jun.thingsflow.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.jun.thingsflow.R
import io.jun.thingsflow.model.dto.GithubIssue
import io.jun.thingsflow.view.IssueActivity

class IssueAdapter internal constructor(private val context: Context)
    : RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var items: List<GithubIssue> = emptyList()
    private val imageURL = "https://s3.ap-northeast-2.amazonaws.com/hellobot-kr-test/image/main_logo.png"
    private val webURL = "https://thingsflow.com/ko/home"

    inner class IssueViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val issueTextView: TextView = itemView.findViewById(R.id.issue_textView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): IssueViewHolder {
        val itemView = inflater.inflate(R.layout.item_issue, parent, false)
        return IssueViewHolder(itemView)
    }

    override fun onBindViewHolder(holer: IssueViewHolder, position: Int) {
        items[position].let { item ->
            with(holer) {

                if(position==4) {
                    issueTextView.visibility = View.GONE
                    imageView.visibility = View.VISIBLE

                    Glide.with(context)
                        .load(imageURL)
                        .fitCenter()
                        .into(imageView)

                    imageView.setOnClickListener {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webURL)))
                    }
                }
                else {
                    issueTextView.visibility = View.VISIBLE
                    imageView.visibility = View.GONE

                    issueTextView.text = String.format(context.getString(R.string.issue_info), item.number, item.title)
                    issueTextView.setOnClickListener {
                        context.startActivity(Intent(context, IssueActivity::class.java)
                            .putExtra("login", item.user.login)
                            .putExtra("avatar_url", item.user.avatar_url)
                            .putExtra("number", item.number)
                            .putExtra("body", item.body))
                    }
                }
            }
        }
    }

    fun setIssue(issues: List<GithubIssue>) {
        items = issues
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}

