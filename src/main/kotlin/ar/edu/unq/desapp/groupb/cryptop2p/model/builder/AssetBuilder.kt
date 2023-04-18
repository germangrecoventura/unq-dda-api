package ar.edu.unq.desapp.groupb.cryptop2p.model.builder

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import java.time.LocalDateTime

class AssetBuilder {
    private var name: String? = null
    private var created: LocalDateTime? = null

    fun build(): Asset {
        val asset = Asset()
        asset.symbol = name
        asset.created = created
        return asset
    }

    fun withName(name: String?): AssetBuilder {
        this.name = name
        return this
    }

    fun withCreated(date: LocalDateTime?): AssetBuilder {
        this.created = date
        return this
    }
}
