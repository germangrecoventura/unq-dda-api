package ar.edu.unq.desapp.groupb.cryptop2p.persistence

import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AssetPriceRepository : CrudRepository<AssetPrice, Long> {
    @Query(value = "SELECT p FROM AssetPrice p WHERE p.asset.name = :assetName ORDER BY p.created DESC LIMIT 1")
    fun findCurrentPriceByAssetName(@Param("assetName") assetName: String): AssetPrice

    @Query(
        value = """
            SELECT p.* FROM asset_price p 
            INNER JOIN (
                SELECT max(i.created) AS latest_date, i.asset_id AS asset_id
                FROM asset_price AS i
                GROUP BY i.asset_id
            ) AS r ON p.created = r.latest_date AND p.asset_id = r.asset_id
        """,
        nativeQuery = true
    )
    fun findLatestPrices(): Collection<AssetPrice>
}
