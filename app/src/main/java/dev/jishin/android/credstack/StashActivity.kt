package dev.jishin.android.credstack

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class StashActivity : AppCompatActivity() {

    private val stashVM by viewModels<StashVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stash)
    }
}