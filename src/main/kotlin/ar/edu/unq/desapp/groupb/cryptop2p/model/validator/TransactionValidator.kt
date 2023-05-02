package ar.edu.unq.desapp.groupb.cryptop2p.model.validator

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.OfferRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import org.springframework.stereotype.Component

@Component
class TransactionValidator(
    val offerRepository: OfferRepository,
    val userRepository: UserRepository
) {
    fun isCreationRequestValid(userId: Long, idOffer: Long): Boolean {
        val optionalUser = userRepository.findById(userId)
        if (optionalUser.isEmpty) {
            throw UserNotRegisteredException()
        }
        val optionalOffer = offerRepository.findById(idOffer)
        if (optionalOffer.isEmpty) {
            throw OfferRegisteredException()
        }
        return true
    }
}

class OfferRegisteredException :
    ModelException("The offer name is not registered", "offer.id")