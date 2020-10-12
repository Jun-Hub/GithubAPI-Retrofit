package io.jun.thingsflow.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import io.jun.thingsflow.R
import kotlinx.android.synthetic.main.activity_issue.*

class IssueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue)

        title = "#${intent.getIntExtra("number", 0)}"

        Glide.with(this)
            .load(intent.getStringExtra("avatar_url"))
            .fitCenter()
            .into(avatar_imageView)

        login_textView.text = intent.getStringExtra("login")
        body_textView.text = intent.getStringExtra("body")
    }
}