package ar.edu.unq.desapp.groupb.cryptop2p.model

import java.time.LocalDateTime

class TradedVolumeReport(
    val user: User,
    val requestDateTime: LocalDateTime,
    val totalAmountUSD: Double,
    val totalAmountARS: Double,
    val lineItems: List<TradedVolumeReportLineItem>,
)

class TradedVolumeReportLineItem(
    val asset: String,
    val quantity: Double,
    val unitPrice: Double,
    val totalAmountInARS: Double,
)
