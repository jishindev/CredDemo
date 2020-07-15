package dev.jishin.android.credstack

import androidx.lifecycle.ViewModel
import dev.jishin.android.credstack.data.models.Stash

class StashVM : ViewModel() {
    val stash by lazy { Stash() }
}