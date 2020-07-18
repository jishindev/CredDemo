package dev.jishin.android.credstack

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stash.*

class StashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stash)

        initViews()
    }

    private fun initViews() {

        // Update views(buttons etc.) when the stack updates
        stackLayout.observeStackChange {
            updateActionButton(it)
        }

        btnAction.setOnClickListener { stackLayout.gotoNext() }

        ivClose.setOnClickListener {
            with(PopupMenu(this, ivClose)) {
                menuInflater.inflate(R.menu.reset, menu)
                setOnMenuItemClickListener {
                    if (it.itemId == R.id.actionReset) {
                        stackLayout.reset()
                    }
                    return@setOnMenuItemClickListener true
                }
                show()
            }
        }
        ivHelp.setOnClickListener {
            with(PopupMenu(this, ivHelp)) {
                menuInflater.inflate(R.menu.help, menu)
                setOnMenuItemClickListener {
                    if (it.itemId == R.id.actionCall) {
                        startActivity(
                            Intent(
                                Intent.ACTION_DIAL,
                                Uri.fromParts("tel", "+919845751282", null)
                            )
                        )
                    }
                    return@setOnMenuItemClickListener true
                }
                show()
            }
        }
    }

    // Updates button text based on the current state
    private fun updateActionButton(currentStackPos: Int) {
        btnAction.text = getString(
            when (currentStackPos) {
                0 -> R.string.proceed_to_emi_selection
                1 -> R.string.select_bank_account
                else -> R.string.tap_for_1_click_kyc
            }
        )
    }

    override fun onBackPressed() {

        // delegate to stackLayout
        if (!stackLayout.gotoPrevious())
            super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}