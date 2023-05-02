package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Offer
import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import ar.edu.unq.desapp.groupb.cryptop2p.model.TransactionStatus
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.OfferValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.OfferRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserTransactionRatingRepository
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.OfferActiveDTO
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
    private val userTransactionRatingRepository: UserTransactionRatingRepository,
    private val offerValidator: OfferValidator
) {
    fun save(offerRequestDTO: OfferRequestDTO): Offer {
        offerValidator.isCreationRequestValid(offerRequestDTO)
        val asset = assetRepository.findByName(offerRequestDTO.asset!!).get()
        val user = userRepository.findById(offerRequestDTO.user!!).get()
        /*val fivePercent = asset.prices.last().unitPrice!! * 5.0 / 100
        val isActive = offerRequestDTO.unitPrice!! <= (asset.prices.last().unitPrice!! + fivePercent)
                &&
                offerRequestDTO.unitPrice!! >= (asset.prices.last().unitPrice!! - fivePercent)*/
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

    fun getOffersActive(asset: String?): List<OfferActiveDTO> {
        if (asset == null) {
            return offerRepository.findAll().filter { offer: Offer? -> offer!!.isActive == true }.map { offer ->
                val offerActive = OfferActiveDTO()
                val listUserRating =
                    userTransactionRatingRepository.findAll()
                        .filter { userRating -> userRating.user!! == offer.user && userRating.transaction!!.status != TransactionStatus.CANCELED }
                val rating =
                    if (listUserRating.isEmpty()) {
                        "Without operations"
                    } else {
                        listUserRating.sumOf { user -> 0 + user.rating!! }.toString()
                    }
                offerActive.date = offer.created
                offerActive.asset = offer.asset
                offerActive.quantity = offer.quantity
                offerActive.unitPrice = offer.unitPrice
                offerActive.totalAmount = offer.totalAmount
                offerActive.firstName = offer.user!!.firstName
                offerActive.lastName = offer.user!!.lastName
                offerActive.operation = offer.operation
                offerActive.sizeOperations = listUserRating.size
                offerActive.rating = rating
                offerActive
            }
        } else {
            return offerRepository.findAll()
                .filter { offer: Offer? -> offer!!.isActive == true && offer!!.asset!!.name == asset }.map { offer ->
                    val offerActive = OfferActiveDTO()
                    val listUserRating =
                        userTransactionRatingRepository.findAll()
                            .filter { userRating -> userRating.user!! == offer.user && userRating.transaction!!.status != TransactionStatus.CANCELED }
                    val rating =
                        if (listUserRating.isEmpty()) {
                            "Without operations"
                        } else {
                            listUserRating.sumOf { user -> 0 + user.rating!! }.toString()
                        }
                    offerActive.date = offer.created
                    offerActive.asset = offer.asset
                    offerActive.quantity = offer.quantity
                    offerActive.unitPrice = offer.unitPrice
                    offerActive.totalAmount = offer.totalAmount
                    offerActive.firstName = offer.user!!.firstName
                    offerActive.lastName = offer.user!!.lastName
                    offerActive.operation = offer.operation
                    offerActive.sizeOperations = listUserRating.size
                    offerActive.rating = rating
                    offerActive
                }
        }
    }

    fun clear() {
        offerRepository.deleteAll()
    }
}