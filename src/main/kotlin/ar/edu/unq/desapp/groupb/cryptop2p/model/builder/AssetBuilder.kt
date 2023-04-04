package ar.edu.unq.desapp.groupb.cryptop2p.model.builder

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import java.time.LocalDateTime
import java.time.LocalTime

class AssetBuilder {
    private var name: String? = null
    private var unitPrice: Double? = null
    private var dayTime: LocalDateTime? = null

    fun build(): Asset {
        var asset = Asset()
        asset.name = name
        asset.dayTime = dayTime
        asset.unitPrice = unitPrice
        return asset
    }

    fun withName(name: String?): AssetBuilder {
        this.name = name
        return this
    }

    fun withDay(date: LocalDateTime?): AssetBuilder {
        this.dayTime = date
        return this
    }

    fun withUnitPrice(unitPrice: Double?): AssetBuilder {
        this.unitPrice = unitPrice
        return this
    }


}
