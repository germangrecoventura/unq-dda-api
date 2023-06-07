package ar.edu.unq.desapp.groupb.cryptop2p.model

import ar.edu.unq.desapp.groupb.cryptop2p.webservice.helpers.View
import com.fasterxml.jackson.annotation.JsonView
import java.time.LocalDateTime

@JsonView(View.Public::class)
data class TradedVolumeReport(
    val user: User,
    val requestDateTime: LocalDateTime,
    val totalAmountUSD: Double,
    val totalAmountARS: Double,
    val lineItems: List<TradedVolumeReportLineItem>,
)

@JsonView(View.Public::class)
data class TradedVolumeReportLineItem(
    val asset: String,
    val quantity: Double,
    val unitPrice: Double,
    val totalAmountInARS: Double,
)
