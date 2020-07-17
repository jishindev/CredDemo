package dev.jishin.android.credstack

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.res.use

fun MotionLayout.onTransition(action: (currentId: Int) -> Unit) {
    addTransitionListener(object : MotionLayout.TransitionListener {
        override fun onTransitionTrigger(
            ml: MotionLayout?,
            startId: Int,
            endId: Boolean,
            progress: Float
        ) {

        }

        override fun onTransitionStarted(ml: MotionLayout?, startId: Int, endId: Int) {
        }

        override fun onTransitionChange(
            ml: MotionLayout?,
            startId: Int,
            endId: Int,
            progress: Float
        ) {
        }

        override fun onTransitionCompleted(ml: MotionLayout?, currentId: Int) {
            action(currentId)
        }
    })

}

fun View.toggleVisibility(show: Boolean, gone: Boolean = false) {
    visibility = if (show) View.VISIBLE else if (gone) View.GONE else View.INVISIBLE
}
