package ar.edu.unq.desapp.groupb.cryptop2p.model.validator

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.AssetRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.OfferRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.OfferRequestDTO
import org.springframework.stereotype.Component

@Component
class OfferValidator(
    val offerRepository: OfferRepository,
    val userRepository: UserRepository,
    val assetRepository: AssetRepository
) {
    fun isCreationRequestValid(offer: OfferRequestDTO): Boolean {
        val optionalAsset = assetRepository.findByName(offer.asset!!)
        if (optionalAsset.isEmpty) {
            throw AssetNameNotRegisteredException()
        }
        val optionalUser = userRepository.findById(offer.user!!)
        if (optionalUser.isEmpty) {
            throw UserNotRegisteredException()
        }
        if (offer.quantity!! < 0) throw QuantityException()
        if (offer.unitPrice!! < 0) throw UnitPriceException()
        if (offer.totalAmount!! < 0) throw TotalAmountException()
        try {
            OfferType.valueOf(offer.operation!!)
            return true
        } catch (e: IllegalArgumentException) {
            throw TypeOperationException()
        }
    }
}

class UserNotRegisteredException :
    ModelException("The user is not registered", "offer.user")


class AssetNameNotRegisteredException :
    ModelException("The asset name is not registered", "offer.name")

class QuantityException :
    ModelException("The quantity can't be negative", "offer.quantity")

class UnitPriceException :
    ModelException("The unit price can't be negative", "offer.unitPrice")

class TotalAmountException :
    ModelException("The total amount can't be negative", "offer.totalAmount")

class TypeOperationException :
    ModelException("The type of operation is invalid", "offer.operation")