package ar.edu.unq.desapp.groupb.cryptop2p.model

import java.time.LocalDateTime

class TradedVolume {
    var dayTime: LocalDateTime? = null
    var totalValueInDollars: Double? = null
    var totalValueInPesos: Double? = null
    var assets: List<Report>? = null

    companion object {
        fun fromModel(assets: List<String>): TradedVolume {
            var traded = TradedVolume()
            var list = listOf("Activo1", "Activo2")
            traded.dayTime = LocalDateTime.now()
            traded.totalValueInDollars = 0.0 // Pegarle a API
            traded.totalValueInPesos = 0.0  // Pegarle a API
            traded.assets = list.map { asset -> Report.fromModel(asset, 20.00, 20.00, 20.00) }

            return traded
        }
    }
}
