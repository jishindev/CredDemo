package dev.jishin.android.credstack.data.models

data class Stash(
    var creditAmount: Int? = null,
    var stashPlan: StashPlan? = null,
    var paymentBank: PaymentBank? = null
)

data class StashPlan(
    val emi: Int = -1,
    val periodInMonths: Int = -1
)

data class PaymentBank(
    val bankName: String = "",
    val accountNo: Long = -1L
)