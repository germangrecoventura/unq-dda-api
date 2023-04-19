package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import java.time.LocalDateTime

class TradedVolumeDTO(
    var requestDateTime: LocalDateTime = LocalDateTime.now(),
    var totalAmountUSD: Double = 0.0,
    var totalAmountARS: Double = 0.0,
    var assets: MutableList<AssetReportDTO>? = mutableListOf(),
)
