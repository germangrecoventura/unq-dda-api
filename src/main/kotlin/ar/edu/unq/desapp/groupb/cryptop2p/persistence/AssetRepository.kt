package ar.edu.unq.desapp.groupb.cryptop2p.persistence

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetRepository : CrudRepository<Asset, Long>
