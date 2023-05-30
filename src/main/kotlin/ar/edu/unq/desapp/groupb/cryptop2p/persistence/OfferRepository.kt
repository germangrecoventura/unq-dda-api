package ar.edu.unq.desapp.groupb.cryptop2p.persistence

import ar.edu.unq.desapp.groupb.cryptop2p.model.Offer
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OfferRepository : CrudRepository<Offer, Long> {
    fun getByIsActive(isActive: Boolean): List<Offer>

    @Query(value = "SELECT o FROM Offer o WHERE o.isActive = :isActive AND o.asset.name = :assetName")
    fun getByIsActiveAndAsset(@Param("isActive") isActive: Boolean, @Param("assetName") assetName: String): List<Offer>
}
