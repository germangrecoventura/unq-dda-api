package ar.edu.unq.desapp.groupb.cryptop2p.service

import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import ar.edu.unq.desapp.groupb.cryptop2p.model.Transaction
import ar.edu.unq.desapp.groupb.cryptop2p.model.TransactionStatus
import ar.edu.unq.desapp.groupb.cryptop2p.model.validator.TransactionValidator
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.OfferRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.TransactionRepository
import ar.edu.unq.desapp.groupb.cryptop2p.persistence.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository,
    private val offerRepository: OfferRepository,
    private val transactionValidator: TransactionValidator,
) {
    fun save(userId: Long, offerId: Long): Transaction {
        transactionValidator.isCreationRequestValid(userId, offerId)
        val user = userRepository.findById(userId).get()
        val offer = offerRepository.findById(offerId).get()
        val fivePercent = offer.asset!!.prices.last().unitPrice!! * 5.0 / 100
        val isActive = offer.unitPrice!! <= (offer.asset!!.prices.last().unitPrice!! + fivePercent)
                &&
                offer.unitPrice!! >= (offer.asset!!.prices.last().unitPrice!! - fivePercent)
        val buyer = if (offer.operation == OfferType.BUY) {
            offer.user
        } else {
            user
        }
        val seller = if (offer.operation == OfferType.SELL) {
            offer.user
        } else {
            user
        }
        if (!isActive) {
            offer.isActive = false
            offerRepository.save(offer)
            val transaction = Transaction().fromModel(
                offer.asset!!,
                offer.quantity!!,
                offer.unitPrice!!,
                offer.totalAmount!!,
                offer,
                seller!!,
                buyer!!
            )
            transaction.status = TransactionStatus.CANCELED
            return transactionRepository.save(transaction)
        } else {
            val transaction = Transaction().fromModel(
                offer.asset!!,
                offer.quantity!!,
                offer.unitPrice!!,
                offer.totalAmount!!,
                offer,
                seller!!,
                buyer!!
            )
            return transactionRepository.save(transaction)
        }
    }


    fun clear() {
        transactionRepository.deleteAll()
    }
}