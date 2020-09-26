package dev.jishin.android.credstack

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout


val View.animDuration: Long
    get() = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()

fun View.toggleVisibility(show: Boolean, gone: Boolean = false) {
    visibility = if (show) View.VISIBLE else if (gone) View.GONE else View.INVISIBLE
}
