package ar.edu.unq.desapp.groupb.cryptop2p.persistence

import ar.edu.unq.desapp.groupb.cryptop2p.model.Offer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OfferRepository : CrudRepository<Offer, Long>
