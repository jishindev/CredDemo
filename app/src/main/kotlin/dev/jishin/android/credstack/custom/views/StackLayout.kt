package dev.jishin.android.credstack.custom.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import dev.jishin.android.credstack.toggleVisibility

class StackLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var onStackChange: () -> Unit = {}
    var currentStackItemPos = 0

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
            val stackItem = getChildAt(i) as? StackItem
            stackItem?.run {
                layout(left, topMargin, right, bottom)

                toggleVisibility(i <= currentStackItemPos)
                setIsExpanded(i == currentStackItemPos, false)
                topMargin += getCollapsedBottom()
                tag = i
                if (i != currentStackItemPos)
                    setOnClickListener {
                        // expand this stackItem
                        currentStackItemPos = it.tag as Int
                        invalidate()
                    }
            }
        }
    }

    fun gotoNext(): Boolean {
        if (currentStackItemPos < childCount - 1) {
            currentStackItemPos++
            invalidate()
            onStackChange()
            return true
        }
        return false
    }

    fun gotoPrevious(): Boolean {
        if (currentStackItemPos > 0) {
            currentStackItemPos--
            invalidate()
            onStackChange()
            return true
        }
        return false
    }

    fun observeStackChange(observer: () -> Unit) {
        onStackChange = observer
    }
}