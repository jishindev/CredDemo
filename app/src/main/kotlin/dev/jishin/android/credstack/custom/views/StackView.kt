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
import dev.jishin.android.credstack.animDuration


class StackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle) {

    // custom attributes
    private var expandedView: View? = null
    private var collapsedView: View? = null
    private var bgColor = 0
    private var isExpanded: Boolean = false

    private var onCollapsedClickListener: () -> Unit = {}

    // expand/collapse a StackView
    fun setIsExpanded(value: Boolean) {
        isExpanded = value
        updateStackState()
    }

    init {

        // initialize custom attributes from style/attrs
        context.obtainStyledAttributes(
            attrs, R.styleable.StackView, defStyle, 0
        ).use { typedArray ->

            fun Context.inflateFromAttr(styleableResId: Int): View? =
                runCatching {
                    LayoutInflater.from(this).inflate(
                        typedArray.getResourceIdOrThrow(styleableResId), parent as? ViewGroup, false
                    )
                }.getOrNull()

            // get custom attributes
            expandedView = context.inflateFromAttr(R.styleable.StackView_expandedLayoutId)
            collapsedView = context.inflateFromAttr(R.styleable.StackView_collapsedLayoutId)
            isExpanded = typedArray.getBoolean(R.styleable.StackView_isExpanded, false)
            bgColor =
                typedArray.getColor(
                    R.styleable.StackView_bgColor,
                    Color.BLUE /*for debug*/
                )
        }

        invalidate()
    }

    // rebuild the StackView
    override fun invalidate() {

        removeAllViews()

        // add views
        collapsedView?.let {
            addView(it, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
            it.setOnClickListener { onCollapsedClickListener() }
        }
        expandedView?.let {
            addView(it)
        }

        updateStackState()
        setCardBackgroundColor(bgColor)

        super.invalidate()
    }

    private fun updateStackState() {

        // fade collapsed/expanded view in/out when StackView is expanded/collapsed
        collapsedView?.animate()?.alpha(if (isExpanded) 0f else 1f)
            ?.setDuration(if (isExpanded) animDuration / 2 else animDuration * 2)?.start()

        expandedView?.animate()?.alpha(if (isExpanded) 1f else 0f)
            ?.setDuration(if (isExpanded) animDuration * 2 else animDuration / 2)?.start()

    }

    fun getCollapsedBottom() =
        collapsedView?.bottom ?: 0

    fun onCollapsedClick(onClick: () -> Unit) {
        onCollapsedClickListener = onClick
    }
}