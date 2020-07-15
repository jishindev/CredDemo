package dev.jishin.android.credstack.custom.views

import android.content.Context
import android.widget.FrameLayout
import androidx.annotation.LayoutRes

class StackLayoutItem(
    context: Context,
    @LayoutRes collapsedLayoutId: Int,
    @LayoutRes expandedLayoutId: Int
) : FrameLayout(context) {
}