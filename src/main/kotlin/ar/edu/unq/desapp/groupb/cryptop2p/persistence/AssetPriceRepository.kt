package ar.edu.unq.desapp.groupb.cryptop2p.persistence

import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetPriceRepository : CrudRepository<AssetPrice, Long>
