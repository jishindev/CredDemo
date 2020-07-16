package dev.jishin.android.credstack

import androidx.constraintlayout.motion.widget.MotionLayout

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