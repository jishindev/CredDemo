package dev.jishin.android.credstack.custom.views

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import dev.jishin.android.credstack.animDuration
import dev.jishin.android.credstack.toggleVisibility

class StackLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val stackViews = arrayListOf<StackView>()
    private var onStackChange: (Int) -> Unit = {}
    var currentStackViewPos = 0


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        var topMargin = top
        for (i in 0 until childCount) {

            val stackView = getChildAt(i) as? StackView ?: throw IllegalArgumentException(
                "StackLayout allows only child views of type StackView. " +
                        "${getChildAt(i).javaClass.simpleName} is not allowed here."
            )

            with(stackView) {

                stackViews.add(this)

                layout(left, topMargin, right, bottom)
                topMargin += getCollapsedBottom()
                tag = i
                toggleVisibility(i <= currentStackViewPos)
                onCollapsedClick {
                    val oldCurrentStackViewPos = currentStackViewPos
                    currentStackViewPos = tag as Int
                    refreshStack(oldCurrentStackViewPos)
                }
            }
        }
    }

    fun reset() {
        val oldPos = currentStackViewPos
        currentStackViewPos = 0
        refreshStack(oldPos)
    }

    fun gotoNext() =
        if (currentStackViewPos < childCount - 1) {
            refreshStack(currentStackViewPos++)
            true
        } else false

    fun gotoPrevious() =
        if (currentStackViewPos > 0) {
            refreshStack(currentStackViewPos--)
            true
        } else false

    private fun refreshStack(oldCurrentStackViewPos: Int) {
        if (oldCurrentStackViewPos == currentStackViewPos) return
        stackViews.forEach { item ->
            val pos = (item.tag as Int)
            val show = pos <= currentStackViewPos
            val isCurrentStackView = pos == currentStackViewPos
            val isOldStackView = pos == oldCurrentStackViewPos
            val isGoingToNext = oldCurrentStackViewPos <= currentStackViewPos


            if (show) {
                item.toggleVisibility(true)

                if (isCurrentStackView) {
                    if (isGoingToNext) {
                        item.translationY = bottom.toFloat()
                        item.alpha = 0.5f
                        item.animate()
                            .alpha(1f)
                            .setInterpolator(AccelerateDecelerateInterpolator())
                            .setDuration(animDuration)
                            .translationYBy(-bottom.toFloat())
                            .start()
                    }
                }
                item.setIsExpanded(isCurrentStackView)
            } else {
                if (isOldStackView) {
                    item.alpha = 1f
                    item.animate()
                        .alpha(0.5f)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .setDuration(animDuration)
                        .translationYBy(bottom.toFloat())
                        .withEndAction { item.toggleVisibility(false) }
                        .start()
                } else {
                    item.toggleVisibility(false)
                }
            }
        }
        onStackChange(currentStackViewPos)
    }

    fun observeStackChange(observer: (Int) -> Unit) {
        onStackChange = observer
    }


}