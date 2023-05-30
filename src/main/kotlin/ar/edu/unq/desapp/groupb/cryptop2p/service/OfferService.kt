package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.Offer
import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.OfferValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetPriceRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.OfferRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserTransactionRatingRepository
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.OfferActiveDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.OfferRequestDTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.math.max

@Service
@Transactional
class OfferService(
    private val offerRepository: OfferRepository,
    private val assetPriceRepository: AssetPriceRepository,
    private val userRepository: UserRepository,
    private val userTransactionRatingRepository: UserTransactionRatingRepository,
    private val offerValidator: OfferValidator
) {
    fun save(offerRequestDTO: OfferRequestDTO): Offer {
        offerValidator.isCreationRequestValid(offerRequestDTO)
        val assetPrice = assetPriceRepository.findCurrentPriceByAssetName(offerRequestDTO.asset!!)
        val user = userRepository.findById(offerRequestDTO.user!!).get()
        val fivePercent = assetPrice.unitPrice!! * 0.05
        val isActive = offerRequestDTO.unitPrice!! <= (assetPrice.unitPrice!! + fivePercent)
                &&
                offerRequestDTO.unitPrice!! >= (assetPrice.unitPrice!! - fivePercent)
        val offer =
            Offer(
                assetPrice.asset,
                offerRequestDTO.quantity,
                offerRequestDTO.unitPrice,
                offerRequestDTO.totalAmount,
                user,
                OfferType.valueOf(offerRequestDTO.operation!!),
                isActive,
                LocalDateTime.now(),
            )
        return offerRepository.save(offer)
    }

    fun getActiveOffers(asset: String?): List<OfferActiveDTO> {
        val offers = if (asset == null) {
            offerRepository.getByIsActive(true)
        } else {
            offerRepository.getByIsActiveAndAsset(true, assetName = asset)
        }

        val userIds = offers.map { it.user!!.id!! }.toSet()
        val userRatings = userTransactionRatingRepository.getByUserIn(userIds)
        val ratingsByUser = userRatings.associateBy { it.userId }

        val activeOffers = offers.map {
            val userRating = ratingsByUser.getOrDefault(it.user!!.id!!, null)
            var rating = 0.0
            var totalOperations = 0
            if (userRating != null) {
                rating = max(userRating.rating, 0.0)
                totalOperations = userRating.totalOperations
            }
            val printableUserRating = if (rating == 0.0) "No Operations" else rating.toString()
            val activeOffer = OfferActiveDTO()
            activeOffer.id = it.id
            activeOffer.created = it.created
            activeOffer.assetId = it.asset!!.id
            activeOffer.assetName = it.asset!!.name
            activeOffer.quantity = it.quantity
            activeOffer.unitPrice = it.unitPrice
            activeOffer.totalAmount = it.totalAmount
            activeOffer.firstName = it.user!!.firstName
            activeOffer.lastName = it.user!!.lastName
            activeOffer.operation = it.operation
            activeOffer.totalOperations = totalOperations
            activeOffer.rating = printableUserRating
            activeOffer
        }
        return activeOffers
    }

    fun clear() {
        offerRepository.deleteAll()
    }
}
