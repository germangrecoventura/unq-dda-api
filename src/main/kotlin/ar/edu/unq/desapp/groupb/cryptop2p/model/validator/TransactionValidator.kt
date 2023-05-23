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
    fun isCreationRequestValid(userId: Long, offerId: Long): Boolean {
        userRepository.findById(userId).orElseThrow { throw UserNotRegisteredException() }
        val optionalOffer = offerRepository.findById(offerId).orElseThrow { throw OfferRegisteredException() }
        if (!optionalOffer.isActive!!) {
            throw OfferNotActiveException()
        }
        return true
    }

    fun isCancelTransactionValid(userId: Long, transactionId: Long): Boolean {
        val user = userRepository.findById(userId).orElseThrow { throw UserNotRegisteredException() }
        val transaction =
            transactionRepository.findById(transactionId).orElseThrow { throw TransactionRegisteredException() }
        if (transaction.buyer != user && transaction.seller != user) {
            throw TransactionUserException()
        }
        if (transaction.status == TransactionStatus.CONFIRMED) {
            throw TransactionConfirmedException()
        }
        if (transaction.status == TransactionStatus.CANCELED) {
            throw TransactionCancelException()
        }
        return true
    }

    fun isTransferTransactionValid(userId: Long, transactionId: Long): Boolean {
        val user = userRepository.findById(userId).orElseThrow { throw UserNotRegisteredException() }
        val transaction =
            transactionRepository.findById(transactionId).orElseThrow { throw TransactionRegisteredException() }
        if (transaction.buyer != user) {
            throw TransactionTransferException()
        }
        if (transaction.status != TransactionStatus.WAITING) {
            throw TransactionStatusTransferException()
        }
        return true
    }

    fun isConfirmTransactionValid(userId: Long, transactionId: Long): Boolean {
        val user = userRepository.findById(userId).orElseThrow { throw UserNotRegisteredException() }
        val transaction =
            transactionRepository.findById(transactionId).orElseThrow { throw TransactionRegisteredException() }
        if (transaction.seller != user) {
            throw TransactionConfirmTransferException()
        }
        if (transaction.status != TransactionStatus.TRANSFERRED) {
            throw TransactionStatusConfirmTransferException()
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

class TransactionTransferException :
    ModelException("The user does not have permissions to transfer", "transaction.user")

class TransactionConfirmTransferException :
    ModelException("The user does not have permissions to confirm transfer", "transaction.user")

class TransactionStatusTransferException :
    ModelException("A transfer cannot be made if it is not in the WAITING state", "transaction.status")

class TransactionStatusConfirmTransferException :
    ModelException("A transfer cannot be made if it is not in the TRANSFERRED state", "transaction.status")
