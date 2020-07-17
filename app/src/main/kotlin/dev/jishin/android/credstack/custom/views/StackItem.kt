package dev.jishin.android.credstack.custom.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.res.use
import com.google.android.material.card.MaterialCardView
import dev.jishin.android.credstack.R
import dev.jishin.android.credstack.toggleVisibility


class StackItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle) {

    private var expandedView: View? = null
    private var collapsedView: View? = null
    private var bgColor = 0
    private var isExpanded: Boolean = false

    fun setIsExpanded(value: Boolean, invalidate: Boolean = true) {
        isExpanded = value

        //if (value) expand() else collapse()
        ///* GlobalScope.launch(Dispatchers.Main) { delay(1000);*/ invalidate() /*}*/
        invalidate()
    }

    init {
        context.obtainStyledAttributes(
            attrs, R.styleable.StackItem, defStyle, 0
        ).use { typedArray ->

            fun Context.inflateFromAttr(styleableResId: Int): View? =
                runCatching {
                    LayoutInflater.from(this).inflate(
                        typedArray.getResourceIdOrThrow(styleableResId), parent as? ViewGroup, false
                    )
                }.getOrNull()

            // get custom attributes
            expandedView = context.inflateFromAttr(R.styleable.StackItem_expandedLayoutId)
            collapsedView = context.inflateFromAttr(R.styleable.StackItem_collapsedLayoutId)
            bgColor =
                typedArray.getColor(
                    R.styleable.StackItem_bgColor,
                    Color.BLUE /*for Debug*/
                )
        }

        invalidate()
    }

    private fun expand() {
        collapsedView?.animate()?.alpha(0f)?.scaleYBy(2f)?.setDuration(200)
            ?.withEndAction { collapsedView?.toggleVisibility(false) }?.start()
        expandedView?.animate()?.alpha(1f)?.translationYBy(2f)?.setDuration(200)
            ?.withEndAction { expandedView?.toggleVisibility(true) }?.start()
    }

    private fun collapse() {
        collapsedView?.animate()?.alpha(1f)?.scaleYBy(1f)?.setDuration(200)
            ?.withEndAction { collapsedView?.toggleVisibility(true) }?.start()
        expandedView?.animate()?.alpha(1f)?.translationYBy(-2f)?.setDuration(200)
            ?.withEndAction { expandedView?.toggleVisibility(false) }?.start()
    }

    override fun invalidate() {

        removeAllViews()

        // add views
        collapsedView?.let {
            addView(it, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
            it.toggleVisibility(show = isExpanded.not())
            it.setOnClickListener { performClick() }
        }
        expandedView?.let {
            addView(it)
            it.toggleVisibility(show = isExpanded)
        }

        setCardBackgroundColor(bgColor)

        super.invalidate()
    }

    fun getCollapsedBottom() =
        collapsedView?.bottom ?: 0

}