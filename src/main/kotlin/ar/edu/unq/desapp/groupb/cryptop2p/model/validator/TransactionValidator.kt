package ar.edu.unq.desapp.groupb.cryptop2p.model.validator

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.model.TransactionStatus
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.OfferRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.TransactionRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import org.springframework.stereotype.Component

@Component
class TransactionValidator(
    val offerRepository: OfferRepository,
    val userRepository: UserRepository,
    val transactionRepository: TransactionRepository
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
        if (optionalOffer.get().isActive!!) {
            throw OfferNotActiveException()
        }
        return true
    }

    fun isCancelTransactionValid(userId: Long, transactionId: Long): Boolean {
        val optionalUser = userRepository.findById(userId)
        if (optionalUser.isEmpty) {
            throw UserNotRegisteredException()
        }
        val optionalTransaction = transactionRepository.findById(transactionId)
        if (optionalTransaction.isEmpty) {
            throw TransactionRegisteredException()
        }
        val user = optionalUser.get()
        val transaction = optionalTransaction.get()
        if (transaction.buyer != user && transaction.seller != user) {
            throw TransactionUserException()
        }
        if (transaction.status == TransactionStatus.CONFIRMED) {
            throw TransactionConfirmedException()
        }
        if (optionalTransaction.get().status == TransactionStatus.CANCELED) {
            throw TransactionCancelException()
        }
        return true
    }
}

class OfferRegisteredException :
    ModelException("The offer name is not registered", "offer.id")

class OfferNotActiveException :
    ModelException("The offer is not active", "offer.id")

class TransactionRegisteredException :
    ModelException("The transaction is not registered", "transaction.id")

class TransactionConfirmedException :
    ModelException("Cannot cancel an already confirmed transaction", "transaction.status")

class TransactionCancelException :
    ModelException("Cannot cancel an already canceled transaction", "transaction.status")

class TransactionUserException :
    ModelException("The user does not belong to the current transaction", "transaction.buyer/seller")