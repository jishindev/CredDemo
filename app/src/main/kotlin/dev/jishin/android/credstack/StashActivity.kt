package dev.jishin.android.credstack

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stash_motion.*

class StashActivity : AppCompatActivity() {

    private val stashVM by viewModels<StashVM>()

    private var currentState = R.id.setCreditAmount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stash_motion)

        initViews()
    }

    private fun initViews() {

        motionLayout.onTransition {
            currentState = it
            updateActionButton()
        }


        // Click handling
        btnAction.setOnClickListener {
            when (currentState) {
                R.id.setCreditAmount -> motionLayout.transitionToState(R.id.setEmiPlan)
                R.id.setEmiPlan -> motionLayout.transitionToState(R.id.setPaymentBank)
                R.id.setPaymentBank -> {
                    // todo submit request
                }
            }
        }

        ivClose.setOnClickListener {

            with(PopupMenu(this, ivClose)) {
                menuInflater.inflate(R.menu.reset, menu)
                setOnMenuItemClickListener {
                    if (it.itemId == R.id.actionReset) {
                        motionLayout.transitionToState(R.id.setCreditAmount)
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
    private fun updateActionButton() {
        btnAction.text = getString(
            when (currentState) {
                R.id.setCreditAmount -> R.string.proceed_to_emi_selection
                R.id.setEmiPlan -> R.string.select_bank_account
                else -> R.string.tap_for_1_click_kyc
            }
        )
    }

    override fun onBackPressed() {
        when (currentState) {
            R.id.setEmiPlan ->
                motionLayout.transitionToState(R.id.setCreditAmount)
            R.id.setPaymentBank ->
                motionLayout.transitionToState(R.id.setEmiPlan)
            else ->
                super.onBackPressed()
        }
        updateActionButton()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}