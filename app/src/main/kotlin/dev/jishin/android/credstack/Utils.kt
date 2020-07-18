package dev.jishin.android.credstack

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout


val View.animDuration: Long
    get() = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()


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
