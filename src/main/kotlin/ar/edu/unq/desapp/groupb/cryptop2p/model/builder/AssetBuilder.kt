package ar.edu.unq.desapp.groupb.cryptop2p.model.builder

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import java.time.LocalTime

class AssetBuilder {
    private var name: String? = null
    private var unitPrice: Double? = null
    private var created: LocalTime? = null
    private var updated: LocalTime? = null

    fun build(): Asset {
        var asset = Asset()
        asset.name = name
        asset.unitPrice = unitPrice
        asset.created = created
        asset.updated = updated
        return asset
    }

    fun withName(name: String?): AssetBuilder {
        this.name = name
        return this
    }

    fun withUnitPrice(unitPrice: Double?): AssetBuilder {
        this.unitPrice = unitPrice
        return this
    }

    fun withCreated(date: LocalTime?): AssetBuilder {
        this.created = date
        return this
    }

    fun withUpdated(date: LocalTime?): AssetBuilder {
        this.updated = date
        return this
    }
}
