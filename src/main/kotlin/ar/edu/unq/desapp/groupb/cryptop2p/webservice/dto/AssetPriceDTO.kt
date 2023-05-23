package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import java.time.LocalDateTime

data class AssetPriceDTO(
    val assetName: String,
    val unitPrice: Double,
    val created: LocalDateTime
) {
    companion object {
        fun fromModel(assetPrice: AssetPrice): AssetPriceDTO {
            return AssetPriceDTO(
                assetPrice.asset!!.name!!,
                assetPrice.unitPrice!!,
                assetPrice.created!!
            )
        }
    }
}
