package ar.edu.unq.desapp.groupb.cryptop2p.model.builder

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import java.time.LocalDateTime

class AssetBuilder {
    private var name: String? = null
    private var unitPrice: Double? = null
    private var created: LocalDateTime? = null
    private var updated: LocalDateTime? = null

    fun build(): Asset {
        return Asset(name, unitPrice, created, updated)
    }

    fun withName(name: String?): AssetBuilder {
        this.name = name
        return this
    }

    fun withUnitPrice(unitPrice: Double?): AssetBuilder {
        this.unitPrice = unitPrice
        return this
    }

    fun withCreated(date: LocalDateTime?): AssetBuilder {
        this.created = date
        return this
    }

    fun withUpdated(date: LocalDateTime?): AssetBuilder {
        this.updated = date
        return this
    }
}
