package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Offer
import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.OfferValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.OfferRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.OfferRequestDTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class OfferService(
    private val offerRepository: OfferRepository,
    private val assetRepository: AssetRepository,
    private val userRepository: UserRepository,
    private val offerValidator: OfferValidator
) {
    fun save(offerRequestDTO: OfferRequestDTO): Offer {
        offerValidator.isCreationRequestValid(offerRequestDTO)
        val asset = assetRepository.findByName(offerRequestDTO.asset!!).get()
        val user = userRepository.findById(offerRequestDTO.user!!).get()
        val offer =
            Offer(
                asset,
                offerRequestDTO.quantity,
                offerRequestDTO.unitPrice,
                offerRequestDTO.totalAmount,
                user,
                OfferType.valueOf(
                    offerRequestDTO.operation!!
                ), true, LocalDateTime.now()
            )

        return offerRepository.save(offer)
    }

    fun getOffersActive(): List<Offer> {
        return offerRepository.findAll().filter { offer: Offer? -> offer!!.isActive == true }
    }

    fun clear() {
        offerRepository.deleteAll()
    }
}